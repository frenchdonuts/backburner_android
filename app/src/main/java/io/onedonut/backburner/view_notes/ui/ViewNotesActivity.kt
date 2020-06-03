package io.onedonut.backburner.view_notes.ui

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.touches
import com.jakewharton.rxbinding2.widget.textChanges
import com.uber.autodispose.android.lifecycle.autoDispose
import io.onedonut.backburner.App
import io.onedonut.backburner.R
import io.onedonut.backburner.base.createViewModel
import io.onedonut.backburner.databinding.ActivityNotesBinding
import io.onedonut.backburner.view_notes.ViewNotesComponent
import io.onedonut.backburner.view_notes.vm.VM
import io.onedonut.backburner.write_note.ui.WriteNoteActivity
import io.reactivex.Observable


class ViewNotesActivity: AppCompatActivity(), UI {

    private val etSearch: EditText
        get() = binding.etSearch
    private val rvNotes: RecyclerView
        get() = binding.rvNotes
    private val adapter: NotesAdapter =
        NotesAdapter()
    private val fab: FloatingActionButton
        get() = binding.fab

    override fun events(): Observable<UI.Event> =
        Observable.merge(
            listOf(
                Observable.just(UI.Event.UiInitialized),
                etSearch.textChanges()
                    .map { UI.Event.SearchTextChanged(it) }
            )
        )

    override fun render(states: Observable<UI.State>) {
        states.map { it.items }
            .autoDispose(this)
            .subscribe { meditationItems ->
                adapter.submitList(meditationItems)
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
        // Setup RecyclerView
        rvNotes.adapter = adapter
        rvNotes.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )

        setupEtSearch()

        // Setup FAB
        fab.setOnClickListener {
            val intent = Intent(this, WriteNoteActivity::class.java)
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, activityOptions)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEtSearch() {
        etSearch.textChanges()
            .map { it.isNotEmpty() }
            .distinctUntilChanged()
            .autoDispose(this)
            .subscribe { etSearchDrawableIsVisible ->
                if (etSearchDrawableIsVisible) {
                    etSearch.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_close_grey_24dp,
                        0)
                } else {
                    etSearch.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0)
                }
            }

        val RIGHT_DRAWABLE = 2
        etSearch.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, ev: MotionEvent?): Boolean {
                if (etSearch.compoundDrawables[RIGHT_DRAWABLE] == null) return false
                if (ev?.action != MotionEvent.ACTION_UP) return false
                val rightDrawableLeftBound =
                    etSearch.right - etSearch.compoundDrawables[RIGHT_DRAWABLE].bounds.width()

                if (ev.rawX >= rightDrawableLeftBound) {
                    etSearch.text.clear()
                }

                return false
            }
        })
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