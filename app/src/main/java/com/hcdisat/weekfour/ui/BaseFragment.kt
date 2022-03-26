package com.hcdisat.weekfour.ui

import android.util.Log
import androidx.fragment.app.Fragment
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.models.Jokes
import com.hcdisat.weekfour.ui.state.UIState
import com.hcdisat.weekfour.viewmodels.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * [BaseFragment] is used to control common behavior and state between its children fragments
 */
abstract class BaseFragment : Fragment() {

    // view model used to handle business logic
    protected val jokesViewModel: JokesViewModel by sharedViewModel()

    /**
     * FragmentDialog used to show a selected joke
     */
    private lateinit var dialogFragment: JokeDialogFragment

    /**
     * handles UI Error state
     */
    private fun showError(throwable: Throwable) {
        Log.d("TAG", throwable.localizedMessage!!)
        canRequestJoke(true)
    }

    /**
     * Displays the joke too the user
     */
    protected fun <T> handleJoke(response: T) {
        if (response is Joke)
            showJoke(response)
        if (response is Jokes)
            showJoke(response.value)
    }

    /**
     * Displays the joke too the user
     */
    private fun showJoke(response: Joke) {
        jokesViewModel.selectedJoke = response
        dialogFragment = JokeDialogFragment
            .newRandomJokeDialog().apply {
                isCancelable = false
            }

        dialogFragment.show(
            requireActivity().supportFragmentManager,
            JokeDialogFragment.TAG
        )

        jokesViewModel.resetUIState()
    }

    /**
     * handles UI State through [UIState]
     */
    protected open fun handleResponse(uiState: UIState) {
        when (uiState) {
            is UIState.SUCCESS<*> -> handleJoke(uiState.response)
            is UIState.LOADING -> canRequestJoke(false)
            is UIState.DEFAULT -> canRequestJoke(true)
            is UIState.ERROR -> showError(uiState.throwable)
        }
    }

    /**
     * handles main views visibility and loading behavior
     */
    protected open fun canRequestJoke(canRequestJoke: Boolean = true) {}
}