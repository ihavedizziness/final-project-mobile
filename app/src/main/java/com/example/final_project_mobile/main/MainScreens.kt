package com.example.final_project_mobile.main

import com.example.final_project_mobile.collection.presentation.CollectionFragment
import com.example.final_project_mobile.details.presentation.FilmDetailFragment
import com.example.final_project_mobile.navigation.fragmentScreenArg
import com.github.terrakok.cicerone.androidx.FragmentScreen

object MainScreens {

    class CollectionScreen(args: CollectionFragment.Args) :
        FragmentScreen by fragmentScreenArg<CollectionFragment>(arg = args, clearContainer = false)

    class FilmDetailScreen(args: FilmDetailFragment.Args) :
        FragmentScreen by fragmentScreenArg<FilmDetailFragment>(arg = args, clearContainer = false)
}