package com.example.moviesinfo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesinfo.databinding.ItemViewMoviesListBinding
import com.example.moviesinfo.views.models.SearchItem

class MoviesListAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MoviesListAdapter.MyViewHolder>() {

    private var moviesList = emptyList<SearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemViewMoviesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            itemClickListener,
            ::getItem
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun submitList(list: List<SearchItem>) {
        val diffUtil =
            DiffUtilCallBack(moviesList, list)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        moviesList = list
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun getItem(position: Int): SearchItem {
        return moviesList[position]
    }

    class MyViewHolder(
        private val binding: ItemViewMoviesListBinding,
        itemClickListener: OnItemClickListener,
        getItem: (Int) -> SearchItem
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(movieData: SearchItem) {
            Glide.with(itemView.context).load(movieData.poster).centerCrop().into(binding.ivImagePoster)
            binding.tvTitle.text = movieData.title
            binding.tvYear.text = movieData.year
        }
    }


    class DiffUtilCallBack(
        private val oldList: List<SearchItem>,
        private val newList: List<SearchItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].imdbID == newList[newItemPosition].imdbID
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].imdbID == newList[newItemPosition].imdbID
                    && oldList[oldItemPosition].title == newList[newItemPosition].title
                    && oldList[oldItemPosition].year == newList[newItemPosition].year
                    && oldList[oldItemPosition].type == newList[newItemPosition].type
                    && oldList[oldItemPosition].poster == newList[newItemPosition].poster
        }

    }

    interface OnItemClickListener {
        fun onItemClick(movieData: SearchItem)
    }
}