package com.hcdisat.weekfour.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hcdisat.weekfour.databinding.FragmentErrorDialogBinding
import com.hcdisat.weekfour.viewmodels.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ErrorDialogFragment: DialogFragment() {

    private val jokesViewModel: JokesViewModel by sharedViewModel()

    private val binding by lazy {
        FragmentErrorDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.exceptionText.text = jokesViewModel.stateError
        binding.btnDismiss.setOnClickListener { dismissAllowingStateLoss()}
        return binding.root
    }

    companion object {
        const val TAG = "ErrorDialog"
        fun newErrorDialogFragment() =
            ErrorDialogFragment().apply {
                setStyle(DialogFragment.STYLE_NO_FRAME, 0)
            }
    }
}