package io.onedonut.backburner.write_note.ui

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
import io.onedonut.backburner.databinding.FragmentWriteNoteBinding
import io.reactivex.Observable

class WriteNoteFragment : UI, Fragment() {

    private val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, ViewModelFactory.instance(requireContext()))
            .get(VM::class.java)
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
