package com.example.final_project_mobile.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_mobile.collection.presentation.CollectionFragment
import com.example.final_project_mobile.core.Action
import com.example.final_project_mobile.core.Resource
import com.example.final_project_mobile.details.presentation.FilmDetailFragment
import com.example.final_project_mobile.main.MainScreens
import com.example.final_project_mobile.home.domain.entity.FilmCollection
import com.example.final_project_mobile.home.domain.usecase.GetFilmsByCollectionUseCase
import com.example.final_project_mobile.navigation.FlowRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val router: FlowRouter,
    private val getMoviesByCollectionUseCase: GetFilmsByCollectionUseCase,
) : ViewModel() {

    private val _moviesFlow = MutableStateFlow<Resource<List<FilmCollection>>>(Resource.Initial)
    val moviesFlow: StateFlow<Resource<List<FilmCollection>>> = _moviesFlow.asStateFlow()

    init {
        getAllMovies()
    }

    fun dispatch(action: Action) = viewModelScope.launch {
        when (action) {
            is HomeAction.OnRefresh -> getAllMovies()
            is HomeAction.NavigateToCollectionScreen -> navigateToCollectionScreen(action.collection)
            is HomeAction.NavigateToFilmDetailsScreen -> navigateToFilmDetailsScreen(action.filmId)
        }
    }

    private fun getAllMovies() = viewModelScope.launch {
        val allCollections = CollectionKey.entries
        _moviesFlow.value = Resource.Loading(count = allCollections.size)

        val filmCollections = mutableListOf<FilmCollection>()
        val errors = mutableListOf<Throwable>()

        allCollections.forEach {
            getMoviesByCollectionUseCase(it.value, 1)
                .onSuccess { collection ->
                    filmCollections.add(collection.copy(collection = it))
                }
                .onFailure { throwable ->
                    errors.add(throwable)
                }
        }

        _moviesFlow.value = if (filmCollections.isEmpty()) {
            Resource.Failure(Throwable("No data"))
        } else if (errors.isNotEmpty()) {
            Resource.Failure(errors.first())
        } else {
            Resource.Success(filmCollections)
        }
    }

    private fun navigateToCollectionScreen(collection: CollectionKey) {
        router.navigateTo(
            MainScreens.CollectionScreen(
                CollectionFragment.Args(collection = collection)
            )
        )
    }

    private fun navigateToFilmDetailsScreen(filmId: Long) {
        router.navigateTo(MainScreens.FilmDetailScreen(FilmDetailFragment.Args(filmId = filmId)))
    }

    sealed interface HomeAction : Action {
        data object OnRefresh : HomeAction
        data class NavigateToCollectionScreen(val collection: CollectionKey) : HomeAction
        data class NavigateToFilmDetailsScreen(val filmId: Long) : HomeAction
    }
}

enum class CollectionKey(val value: String, val title: String) {
    TopPopularCollection("TOP_POPULAR_MOVIES", "Топ Популярных"),
    VampireCollection("VAMPIRE_THEME", "Вампиры"),
    ComicsCollection("COMICS_THEME", "Комиксы");
}