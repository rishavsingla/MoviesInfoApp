package com.example.moviesinfo.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesinfo.R
import com.example.moviesinfo.databinding.FragmentMoviesListBinding
import com.example.moviesinfo.util.Resource
import com.example.moviesinfo.views.adapters.MoviesListAdapter
import com.example.moviesinfo.views.models.SearchItem
import com.example.moviesinfo.views.viewmodels.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies_list.*

@AndroidEntryPoint
class MoviesListFragment : Fragment(), MoviesListAdapter.OnItemClickListener {

    private val viewModel: MoviesListViewModel by viewModels()
    private var binding: FragmentMoviesListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        val moviesAdapter = MoviesListAdapter(this)
        binding.recyclerview.adapter = moviesAdapter

        viewModel.getMoviesList().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.progressIndicator.isVisible = true
                is Resource.Error -> {
                    binding.progressIndicator.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.progressIndicator.isVisible = false
                    it.data?.let { list ->
                        moviesAdapter.submitList(list)
                    }
                }
            }
        }
        binding.simpleSearchView.isIconified = false
        binding.simpleSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchedMovie(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getSearchedMovie(newText)
                return false
            }

        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onItemClick(movieData: SearchItem) {
        findNavController().navigate(
            R.id.action_moviesListFragment_to_movieDetailFragment,
            Bundle().apply {
                putString("selected_movie_id", movieData.imdbID)
            })
    }


}