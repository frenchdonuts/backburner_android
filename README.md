# backburner(WIP)

This is an app I've been thinking about making for a while. Its purpose is to remind the user of notes left on the back-burner without desensitizing the user to the notifications (insta-dismiss). It does this by making sure the time interval between notifications is fairly long (7-14 days), and random. It also serves as a demonstration of my current understanding of Android architecture.

## General architecture

* UI interface: Implementers emit UI events and read off the State that has been computed, turning them into side-effects that render to screen.
*     events :: Unit -> Observable<UI.Event>
*     render :: Observable<UI.UIState> -> Unit
* VM interface: Implementers transform UI events into business-logic Msgs and reduce on those Msgs w/ current state.
*     processEvents :: Observable<UI.Event> -> Unit
*     states :: Unit -> Observable<UI.UIState>
* Dataflow:
*     Data sources -> Repositories -> Interactors -> VM -> UI
*     UI -> VM -> Interactors -> Repositories -> Data sources

## TODO

* [x] Integrate SQLDelight and create Note table + queries
* [x] Implement data flow for Notes screen 
* [ ] Implement data flow for WriteNote screen
* [ ] Implement random notifications
* [ ] Refine UX and UI
* [x] Use Dagger to inject dependencies
* [ ] Import pinned Google Keep notes
* [ ] Allow User to customize the range of time interval between notifications.
