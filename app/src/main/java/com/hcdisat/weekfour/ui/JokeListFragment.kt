package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.weekfour.adapters.JokesAdapter
import com.hcdisat.weekfour.databinding.FragmentJokeListBinding
import com.hcdisat.weekfour.models.JokeList
import com.hcdisat.weekfour.network.EndPoints
import com.hcdisat.weekfour.ui.state.UIState

class JokeListFragment : BaseFragment() {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!
    private var isListLoading = false

    private val jokesAdapter by lazy {
        JokesAdapter() {
            handleJoke(it)
        }
    }

    private fun setJokeList() {
        binding.jokeList.apply {
            val linearLayoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            layoutManager = linearLayoutManager

            adapter = jokesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!isListLoading) {
                        if (linearLayoutManager.findLastVisibleItemPosition() == jokesAdapter.itemCount - 1) {
                            jokesViewModel.getJoke()
                        }
                    }
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeListBinding.inflate(inflater, container, false)

        jokesViewModel.randomJoke.observe(viewLifecycleOwner, ::handleResponse)
        jokesViewModel.endPoint = EndPoints.RANDOM_LIST
        setJokeList()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        jokesViewModel.getJoke()
    }

    override fun handleResponse(uiState: UIState) {
        when (uiState) {
            is UIState.SUCCESS<*> -> {
                jokesAdapter.restore()
                jokesAdapter.append((uiState.response as JokeList).value)
                isListLoading = false
            }
            is UIState.LOADING -> {
                isListLoading = true
                jokesAdapter.setLoading()
            }
            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}