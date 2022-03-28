package com.hcdisat.weekfour.ui

import androidx.fragment.app.Fragment
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.models.Jokes
import com.hcdisat.weekfour.ui.dialogs.ErrorDialogFragment
import com.hcdisat.weekfour.ui.dialogs.JokeDialogFragment
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
     * handles UI Error state
     */
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    protected open fun showError(throwable: Throwable) {
        showErrorDialog(throwable.localizedMessage)
        canRequestJoke(true)
    }

    /**
     * show error dialog
     */
    private fun showErrorDialog(errorMessage: String) {
        jokesViewModel.stateError = errorMessage
        (ErrorDialogFragment.newErrorDialogFragment().apply {
            isCancelable = false
        }).show(
            parentFragmentManager,
            ErrorDialogFragment.TAG
        )
        jokesViewModel.resetUIState()
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
        (JokeDialogFragment
            .newRandomJokeDialog().apply {
                isCancelable = false
            }).show(
            parentFragmentManager,
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