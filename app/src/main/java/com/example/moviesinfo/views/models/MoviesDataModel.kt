package com.example.moviesinfo.views.models

import com.google.gson.annotations.SerializedName

data class MoviesDataModel(

	@SerializedName("Response")
	val response: String,

	@SerializedName("totalResults")
	val totalResults: String,

	@SerializedName("Search")
	val search: List<SearchItem>
)

data class SearchItem(

	@SerializedName("Type")
	val type: String,

	@SerializedName("Year")
	val year: String,

	@SerializedName("imdbID")
	val imdbID: String,

	@SerializedName("Poster")
	val poster: String,

	@SerializedName("Title")
	val title: String
)
