package io.onedonut.backburner.write_note.ui

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.uber.autodispose.android.lifecycle.autoDispose
import io.onedonut.backburner.App
import io.onedonut.backburner.R
import io.onedonut.backburner.base.createViewModel
import io.onedonut.backburner.databinding.ActivityWriteNoteBinding
import io.onedonut.backburner.write_note.WriteNoteComponent
import io.onedonut.backburner.write_note.vm.VM
import io.reactivex.Observable

class WriteNoteActivity : AppCompatActivity(), UI {

    private val btAddNote: Button
        get() = binding.btAddNote
    private val etNote: EditText
        get() = binding.etNote

    override fun events(): Observable<UI.Event> {
        return Observable.merge(listOf(
            btAddNote.clicks()
                .map {
                    val noteText = etNote.text.toString()
                    UI.Event.CreateButtonTapped(noteText)
                },
            Observable.just(UI.Event.UIInitialized)
        ))
    }

    override fun render(states: Observable<UI.State>) {
        states
            .autoDispose(this)
            .subscribe()
    }

    private fun connectViewModel() {
        render(viewModel.states())
        viewModel.processEvents(events())
    }

    private fun setTransitionAnimation() {
        val inTransition: Transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.fade_and_slide_in)
        with(inTransition) {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.enterTransition = inTransition
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        featureComponent = (application as App)
            .appComponent
            .writeNoteComponent()
            .create()
        super.onCreate(savedInstanceState)
        setTransitionAnimation()
        supportActionBar?.elevation = 0f
        binding = ActivityWriteNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectViewModel()
    }

//    override fun onBackPressed() {
//        //To support reverse transitions when user clicks the device back button
//        supportFinishAfterTransition()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.getItemId()) {
//            android.R.id.home -> {
//                supportFinishAfterTransition()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private lateinit var featureComponent: WriteNoteComponent
    private val viewModel: VM by lazy {
        createViewModel { featureComponent.vm().get() }
    }

    private lateinit var binding: ActivityWriteNoteBinding
    companion object {
        private val TAG = "WriteNoteActivity"
    }
}