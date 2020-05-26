package io.onedonut.backburner.view_notes.vm

import arrow.syntax.function.pipe
import io.onedonut.backburner.view_notes.interactors.Interactors
import io.onedonut.backburner.view_notes.ui.UI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ViewNotesViewModel @Inject constructor(val interactors: Interactors) : VM() {

    private fun toMsgs(events: Observable<UI.Event>): Observable<Msg> = events.publish { shared ->
        Observable.merge(listOf(
            shared.ofType(UI.Event.UiInitialized::class.java)
                .switchMap { interactors.meditations() },
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
                is Msg.MeditationsLoaded -> {
                    val items = msg.notes
                        .map {
                            UI.Item(
                                it.id,
                                it.text
                            )
                        }
                    state.copy(uiState = state.uiState.copy(items = items))
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
            .pipe { toMsgs(it) }
            .pipe { computeStates(it) }
            .map { it.uiState }
            .subscribe(statesSubject)
    }
    override fun processEvents(events: Observable<UI.Event>) {
        events.subscribe(eventsSubject)
    }

    override fun states(): Observable<UI.State> {
        return statesSubject
    }
}