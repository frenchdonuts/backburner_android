package io.onedonut.backburner.view_notes.vm

import arrow.syntax.function.pipe
import io.onedonut.backburner.view_notes.interactors.Interactors
import io.onedonut.backburner.view_notes.ui.UI
import io.onedonut.backburner.view_notes.ui.items
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ViewNotesViewModel @Inject constructor(val interactors: Interactors) : VM() {

    private fun toMsgs(events: Observable<UI.Event>): Observable<Msg> = events.publish { shared ->
        Observable.merge(listOf(
            shared.ofType(UI.Event.UiInitialized::class.java)
                .switchMap { interactors.meditations() },
            shared.ofType(UI.Event.SearchTextChanged::class.java)
                .skip(1)
                .debounce(300, TimeUnit.MILLISECONDS, Schedulers.io())
                .switchMapSingle { interactors.searchNotes(it.query) },
            shared.ofType(UI.Event.UiRecreated::class.java)
                .map { Msg.NoOp }
        ))
    }
    .filter { it !is Msg.NoOp }
    .observeOn(AndroidSchedulers.mainThread())

    private fun computeStates(msgs: Observable<Msg>): Observable<State> = msgs.scan(
        State(),
        { state, msg ->
            when (msg) {
                is Msg.NotesLoaded -> {
                    val items = msg.notes
                        .map {
                            UI.Item(
                                it.id,
                                it.text
                            )
                        }
                    state
                        .pipe { State.notes.set(it, msg.notes) }
                        .pipe { State.uiState.items.set(it, items) }
                }
                is Msg.NotesSearchResult -> {
                    val items = (if (msg.results.isEmpty()) state.notes else msg.results)
                        .map {
                            UI.Item(
                                it.id,
                                it.text
                            )
                        }
                    State.uiState.items.set(state, items)
                }
                is Msg.NoOp -> state
            }
        })

    private val eventsSubject: PublishSubject<UI.Event> = PublishSubject.create()
    private val statesSubject: BehaviorSubject<UI.State> = BehaviorSubject.create()
    private val uiInitializedFilter =
        BiFunction { _ : UI.Event, newEvent: UI.Event ->
        if (newEvent is UI.Event.UiInitialized) {
            UI.Event.UiRecreated
        } else {
            newEvent
        }
    }

    init {
        eventsSubject
            .scan(uiInitializedFilter)
//            .doOnNext { Log.d(TAG, "$it") }
            .pipe { toMsgs(it) }
//            .doOnNext { Log.d(TAG, "msg: $it") }
            .pipe { computeStates(it) }
//            .doOnNext { Log.d(TAG, "state: $it") }
            .map { it.uiState }
            .subscribe(statesSubject)
    }
    override fun processEvents(events: Observable<UI.Event>) {
        events.subscribe(eventsSubject)
    }

    override fun states(): Observable<UI.State> {
        return statesSubject
    }

    companion object {
        private val TAG = "ViewNotesVM"
    }
}