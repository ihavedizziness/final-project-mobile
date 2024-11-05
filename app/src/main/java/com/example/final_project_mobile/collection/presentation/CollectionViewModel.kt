package com.example.final_project_mobile.collection.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.final_project_mobile.collection.domain.usecase.GetPagedCollectionUseCase
import com.example.final_project_mobile.core.Action
import com.example.final_project_mobile.details.presentation.FilmDetailFragment
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.main.MainScreens
import com.example.final_project_mobile.navigation.FlowRouter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val router: FlowRouter,
    args: CollectionFragment.Args,
    getPagedCollectionUseCase: GetPagedCollectionUseCase,
) : ViewModel() {

    val films: Flow<PagingData<Film>> =
        getPagedCollectionUseCase
            .invoke(args.collection.value)
            .cachedIn(viewModelScope)

    fun dispatch(action: Action) = viewModelScope.launch {
        when (action) {
            CollectionAction.OnBackPressed -> router.exit()
            is CollectionAction.NavigateToFilmDetailsScreen -> navigateToFilmDetailsScreen(action.filmId)
        }
    }

    private fun navigateToFilmDetailsScreen(filmId: Long) {
        router.navigateTo(MainScreens.FilmDetailScreen(FilmDetailFragment.Args(filmId = filmId)))
    }

    sealed interface CollectionAction : Action {
        data object OnBackPressed : CollectionAction
        data class NavigateToFilmDetailsScreen(val filmId: Long) : CollectionAction
    }
}