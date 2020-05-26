package io.onedonut.backburner.view_notes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uber.autodispose.android.lifecycle.autoDispose
import io.onedonut.backburner.App
import io.onedonut.backburner.R
import io.onedonut.backburner.base.fragmentViewModels
import io.onedonut.backburner.databinding.FragmentNotesBinding
import io.onedonut.backburner.view_notes.ViewNotesComponent
import io.onedonut.backburner.view_notes.vm.VM
import io.reactivex.Observable

/**
 * Fragment that displays the User's list of Notes
 */
class ViewNotesFragment : Fragment(), UI {

    private lateinit var featureComponent: ViewNotesComponent
    private val viewModel: VM by fragmentViewModels {
        featureComponent.vm().get()
    }
    private val adapter: NotesAdapter =
        NotesAdapter()

    override fun events(): Observable<UI.Event> =
        Observable.merge(
            listOf(
                Observable.just(UI.Event.UiInitialized)
            )
        )

    override fun render(states: Observable<UI.State>) {
        states.map { it.items }
            .autoDispose(viewLifecycleOwner)
            .subscribe { meditationItems ->
                adapter.submitList(meditationItems)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        featureComponent = (requireActivity().application as App)
            .appComponent
            .viewNotesComponent()
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        render(viewModel.states())
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processEvents(events())
        binding.rvMeditations.adapter = adapter
        binding.rvMeditations.layoutManager = GridLayoutManager(context, 2)
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
            it.setOnClickListener { _ ->
                navigateToAddMeditation()
            }
        }
    }

    private fun navigateToAddMeditation() {
        findNavController().navigate(R.id.action_MeditationsFragment_to_AddMeditationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = "MeditationsFragment"
    }
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

}
