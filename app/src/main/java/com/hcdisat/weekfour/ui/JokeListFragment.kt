package com.hcdisat.weekfour.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.weekfour.adapters.JokesAdapter
import com.hcdisat.weekfour.databinding.FragmentJokeListBinding
import com.hcdisat.weekfour.dataaccess.network.EndPoints
import com.hcdisat.weekfour.models.Joke
import com.hcdisat.weekfour.ui.state.UIState

class JokeListFragment : BaseFragment() {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!
    private var isListLoading = false

    private val jokesAdapter by lazy {
        JokesAdapter {
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
                            jokesViewModel.getJokes()
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

        jokesViewModel.jokesState.observe(viewLifecycleOwner, ::handleResponse)
        jokesViewModel.endPoint = EndPoints.RANDOM_LIST
        setJokeList()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        jokesViewModel.getJokes()
    }

    override fun handleResponse(uiState: UIState) {
        when (uiState) {
            is UIState.SUCCESS<*> -> {
                jokesAdapter.restore()
                val jokes = uiState.response as List<*>
                jokesAdapter.append(jokes.filterIsInstance<Joke>())
                isListLoading = false
            }
            is UIState.LOADING -> {
                isListLoading = true
                jokesAdapter.setLoading()
            }
            is UIState.ERROR -> showError(uiState.throwable)
            else -> {
                //no-op
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}