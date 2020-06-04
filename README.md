# backburner(WIP)

This is an app I've been thinking about making for a while. Its purpose is to remind the user of notes left on the back-burner without desensitizing the user to the notifications (insta-dismiss). It does this by making sure the time interval between notifications is fairly long (7-14 days), and random. It also serves as a demonstration of my current understanding of Android architecture. The UI is taken from my favorite note taking app, Google Keep.

## TODO

- [x] Setup Data Access Layer(DAL) 
  - [x] Setup SQLite db using SQLDelight
      - [x] Create Note table
      - [x] Write CRUD queries
      - [x] Create virtual table using FTS4 + triggers to sync data
      - [x] Write Search query
      - [ ] Random note query
  - [x] Create Note model class
  - [x] Implement NoteRepository
      - [x] CRUD + Search
      - [ ] Random note
  - [x] Wrap everything up in a Dagger module
- [ ] Implement View Notes screen 
  - [x] Fetch Notes and display them
  - [x] Search notes
  - [x] Navigate to Write Note screen when fab is tapped
  - [ ] Navigate to Write Note screen when a note is tapped with shared element animations
  - [x] Use Dagger to inject dependencies
  - [x] Create Dagger Subcomponent
  - [ ] Wrap everything up in a Dagger module
  - [ ] XML layout for landscape mode
- [ ] Implement Write Note screen
  - [x] Create note
  - [x] Proper enter transition animation
  - [ ] Have EditText take up whole screen
  - [ ] Save note on back navigation
  - [ ] Proper transition animation w/ shared elements on back navigation
  - [ ] Edit note
  - [ ] Delete note
  - [x] Use Dagger to inject dependencies
  - [x] Create Dagger Subcomponent
  - [ ] Wrap everything up in a Dagger module
- [ ] Implement random notifications
  - [ ] Implement Notifier service
      - [x] Implement notify()
      - [ ] Define notification channel id
  - [ ] Implement Scheduler service
      - [ ] scheduleReminder()
          - [x] Queue RemindUserWorker
          - [ ] Check if RemindUserWorker has already been queued before queuing
      - [ ] makeSureReminderIsScheduled()
          - [x] Queue ScheduleReminderWorker
          - [ ] Check if ScheduleReminderWorker has already been queued before queuing
  - [x] Create scaffolding to allow Dagger to inject into Workers
  - [ ] Implement RemindUserWorker
      - [x] Inject Notifier service
      - [ ] Inject NoteRepository
      - [ ] Implement logic
  - [x] Implement ScheduleReminderWorker
- [ ] Using dagger.android to cleanup injection code
- [ ] Import pinned Google Keep notes
- [ ] Allow User to customize the range of time interval between notifications.
- [ ] Experiment porting over to [Composable Architecture](https://github.com/pointfreeco/swift-composable-architecture)

## General architecture

* UI interface: Implementers emit UI events and read off the State that has been computed, turning them into side-effects that render to screen.
*     events :: Unit -> Observable<UI.Event>
*     render :: Observable<UI.UIState> -> Unit
* VM interface: Implementers (usually subclasses of lifecycle ViewModel) transform UI events into business-logic Msgs and reduce on those Msgs w/ current state.
*     processEvents :: Observable<UI.Event> -> Unit
*     states :: Unit -> Observable<UI.UIState>
* Dependency graph for a given feature (Interactors, VM, UI):
*     UI -> VM -> Interactors -> Repositories -> Data sources
