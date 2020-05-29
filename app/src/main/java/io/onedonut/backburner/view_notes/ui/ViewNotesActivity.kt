package io.onedonut.backburner.view_notes.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uber.autodispose.android.lifecycle.autoDispose
import io.onedonut.backburner.App
import io.onedonut.backburner.base.createViewModel
import io.onedonut.backburner.databinding.ActivityNotesBinding
import io.onedonut.backburner.view_notes.ViewNotesComponent
import io.onedonut.backburner.view_notes.vm.VM
import io.onedonut.backburner.write_note.ui.WriteNoteActivity
import io.reactivex.Observable

class ViewNotesActivity: AppCompatActivity(), UI {

    private val rvNotes: RecyclerView
        get() = binding.rvNotes
    private val adapter: NotesAdapter =
        NotesAdapter()
    private val fab: FloatingActionButton
        get() = binding.fab

    override fun events(): Observable<UI.Event> =
        Observable.merge(
            listOf(
                Observable.just(UI.Event.UiInitialized)
            )
        )

    override fun render(states: Observable<UI.State>) {
        states.map { it.items }
            .autoDispose(this)
            .subscribe { meditationItems ->
                adapter.submitList(meditationItems)
            }
    }

    private fun setupViews() {
        rvNotes.adapter = adapter
        rvNotes.layoutManager = GridLayoutManager(this, 2)
        fab.setOnClickListener {
            val intent = Intent(this, WriteNoteActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    private fun connectViewModel() {
        render(viewModel.states())
        viewModel.processEvents(events())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        featureComponent = (application as App)
            .appComponent
            .viewNotesComponent()
            .create()
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        connectViewModel()
    }

    private lateinit var featureComponent: ViewNotesComponent
    private val viewModel: VM by lazy {
        createViewModel { featureComponent.vm().get() }
    }

    private lateinit var binding: ActivityNotesBinding
    companion object {
        private val TAG = "ViewNotesActivity"
    }
}