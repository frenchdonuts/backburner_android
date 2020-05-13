package io.onedonut.backburner.write_note.vm

import arrow.syntax.function.pipe
import io.onedonut.backburner.write_note.interactors.Interactors
import io.onedonut.backburner.write_note.ui.UI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class WriteNoteViewModel(val interactors: Interactors) : VM() {

    private fun toMsgs(events: Observable<UI.Event>): Observable<Msg> = events.publish { shared ->
        Observable.merge(listOf(
            shared.ofType(UI.Event.CreateButtonTapped::class.java)
                .switchMapSingle {
                    interactors.createMeditation(it.meditationText)
                },
            shared.ofType(UI.Event.UIInitialized::class.java)
                .map { Msg.NoOp }
        ))
    }
    .filter { it !is Msg.NoOp }
    .observeOn(AndroidSchedulers.mainThread())

    private fun computeState(msgs: Observable<Msg>): Observable<State> = msgs.scan(
        State(),
        { state, msg ->
            when (msg) {
                is Msg.NoOp -> state
            }
        })

    private val eventSubject: PublishSubject<UI.Event> = PublishSubject.create()
    private val statesSubject: PublishSubject<UI.State> = PublishSubject.create()
    init {
        eventSubject
            .pipe { toMsgs(it) }
            .pipe { computeState(it) }
            .map { it.uiState }
            .subscribe(statesSubject)
    }
    override fun processEvents(events: Observable<UI.Event>) {
        events.subscribe(eventSubject)
    }

    override fun states(): Observable<UI.State> {
        return statesSubject
    }
}