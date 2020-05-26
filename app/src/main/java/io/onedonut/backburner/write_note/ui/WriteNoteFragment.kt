package io.onedonut.backburner.write_note.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.view.clicks
import io.onedonut.backburner.ViewModelFactory
import io.onedonut.backburner.write_note.vm.VM
import com.uber.autodispose.android.lifecycle.autoDispose
import io.onedonut.backburner.App
import io.onedonut.backburner.base.fragmentViewModels
import io.onedonut.backburner.databinding.FragmentWriteNoteBinding
import io.onedonut.backburner.write_note.WriteNoteComponent
import io.reactivex.Observable

class WriteNoteFragment : UI, Fragment() {

    private lateinit var featureComponent: WriteNoteComponent
    private val viewModel: VM by fragmentViewModels {
        featureComponent.vm().get()
    }

    override fun events(): Observable<UI.Event> {
        return Observable.merge(listOf(
            binding.btAddMeditation.clicks()
                .map {
                    UI.Event.CreateButtonTapped(
                        binding.etMeditation.text.toString()
                    )
                },
            Observable.just(UI.Event.UIInitialized)
        ))
    }

    override fun render(states: Observable<UI.State>) {
        states
            .autoDispose(viewLifecycleOwner)
            .subscribe()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        featureComponent = (requireActivity().application as App)
            .appComponent
            .writeNoteComponent()
            .create()
    }

    private lateinit var binding: FragmentWriteNoteBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.processEvents(events())
        render(viewModel.states())
    }

}
