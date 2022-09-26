package com.example.moviesinfo.views.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesinfo.databinding.FragmentMovieDetailsBinding
import com.example.moviesinfo.util.Resource
import com.example.moviesinfo.views.models.MovieDetailModel
import com.example.moviesinfo.views.viewmodels.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private var binding: FragmentMovieDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val binding = binding ?: return
        val bundle = arguments ?: return

        val args = MovieDetailFragmentArgs.fromBundle(bundle)
        viewModel.getMovieDetails(args.selectedMovieId)


        viewModel.getMovieDetails().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.progressIndicator.isVisible = true
                is Resource.Error -> {
                    binding.progressIndicator.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.progressIndicator.isVisible = false
                    binding.cardView.isVisible=true
                    it.data?.let { movieDetails ->
                        bindData(view.context, movieDetails)
                    }
                }
            }
        }
    }

    private fun bindData(context: Context, movieDetails: MovieDetailModel) {
        val binding = binding ?: return
        Glide.with(context).load(movieDetails.poster).centerCrop().into(binding.ivPoster)
        binding.tvTitle.text = movieDetails.title
        binding.tvRating.text = movieDetails.imdbRating
        binding.tvRuntime.text = movieDetails.runtime
        binding.tvGenre.text = movieDetails.genre
        binding.tvPlot.text = movieDetails.plot
        binding.tvDirector.text = "Director : ${movieDetails.director}"
        binding.tvWriter.text = "Writer : ${movieDetails.writer}"
        binding.tvActors.text = "Actors : ${movieDetails.actors}"
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}