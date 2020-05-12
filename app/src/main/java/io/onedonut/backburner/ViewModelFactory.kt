package io.onedonut.backburner

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arrow.syntax.function.memoize
import io.onedonut.backburner.add_meditation.interactors.InteractorsImpl
import io.onedonut.backburner.add_meditation.vm.AddMeditationViewModel
import io.onedonut.backburner.add_meditation.vm.VM
import io.onedonut.backburner.meditations.MeditationsViewModel
import io.onedonut.backburner.repository.ItemRepositoryImpl
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == io.onedonut.backburner.meditations.VM::class.java)
            return MeditationsViewModel(
                io.onedonut.backburner.meditations.InteractorsImpl(
                    ItemRepositoryImpl(
                        App.db
                    )
                )
            ) as T
        else if (modelClass == VM::class.java)
            return AddMeditationViewModel(
                InteractorsImpl(
                    ItemRepositoryImpl(
                        App.db
                    )
                )
            ) as T

        throw IllegalArgumentException("unknown model class: $modelClass")
    }

    companion object {
        val instance = { context: Context ->
            ViewModelFactory(context.applicationContext)
        }.memoize()
    }
}