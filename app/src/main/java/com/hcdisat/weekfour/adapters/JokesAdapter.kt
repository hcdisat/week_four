package com.hcdisat.weekfour.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hcdisat.weekfour.R
import com.hcdisat.weekfour.databinding.JokeItemBinding
import com.hcdisat.weekfour.models.Joke

class JokesAdapter(
    private val jokes: MutableList<Joke?> = mutableListOf(),
    private val onJokeClicked: (joke: Joke) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * forces the recycler view too show a loading state
     */
    fun setLoading() {
        jokes.add(null)
        notifyItemInserted(itemCount - 1)
    }

    /**
     * removes the loading state from recycler view
     */
    fun restore(notify: Boolean = false) {
        if (jokes.isEmpty()) return
        jokes.removeAt(itemCount - 1)
        if (notify) notifyItemRemoved(itemCount)
    }

    fun append(newJokes: List<Joke>) {
        val lastItem = itemCount
        jokes.addAll(newJokes)
        notifyItemRangeChanged(lastItem, jokes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            JOKE_VIEW_TYPE -> JokesViewHolder(
                JokeItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                onJokeClicked
            )
            else -> LoadingJokesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        jokes[position]?.let {
            (holder as JokesViewHolder).bind(it)
        }
    }

    override fun getItemCount(): Int = jokes.size

    /**
     * this method will decides the type of view t be used
     */
    override fun getItemViewType(position: Int): Int {
        return if (jokes[position] == null)
            LOADING_VIEW_TYPE
        else JOKE_VIEW_TYPE
    }

    companion object {
        private const val JOKE_VIEW_TYPE = 0
        private const val LOADING_VIEW_TYPE = 1
    }
}

/**
 * recycler view item view holder
 */
class JokesViewHolder(
    private val binding: JokeItemBinding,
    private val onJokeClicked: (joke: Joke) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        binding.jokeItem.text = joke.joke
        binding.root.setOnClickListener { onJokeClicked(joke) }
    }
}

/**
 * Loading view holder used to display a progress bar inside recycler view
 */
class LoadingJokesViewHolder(view: View): RecyclerView.ViewHolder(view)