package com.example.final_project_mobile.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.final_project_mobile.R
import com.example.final_project_mobile.collection.presentation.CollectionViewModel
import com.example.final_project_mobile.compose.Shimmer
import com.example.final_project_mobile.compose.defaultMutableInteractionSource
import com.example.final_project_mobile.core.Action
import com.example.final_project_mobile.core.Resource
import com.example.final_project_mobile.home.domain.entity.Film
import com.example.final_project_mobile.home.domain.entity.FilmCollection
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme
import com.example.final_project_mobile.ui.theme.AppTheme
import com.example.final_project_mobile.ui.theme.Graphik
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : FlowItemFragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme {
        HomeContent(onAction = viewModel::dispatch)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeContent(
        onAction: (Action) -> Unit,
    ) {
        val filmCollections by viewModel.moviesFlow.collectAsState()

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.white)
                .padding(
                    top = 16.dp,
                    start = 26.dp,
                    end = 26.dp,
                ),
            isRefreshing = filmCollections is Resource.Loading,
            onRefresh = { onAction.invoke(HomeViewModel.HomeAction.OnRefresh) },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 26.dp, top = 55.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                item {
                    Image(
                        contentDescription = null,
                        painter = painterResource(R.drawable.home_title)
                    )
                }

                when (val value = filmCollections) {
                    is Resource.Success -> {
                        items(value.data) {
                            HomeRecommendationItem(
                                data = it,
                                onAction = onAction,
                            )
                        }
                    }

                    is Resource.Loading -> {
                        items(value.count) {
                            HomeRecommendationLoadingItem()
                        }
                    }

                    is Resource.Failure -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 20.dp),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "Упс, что-то пошло не так",
                                    fontFamily = FontFamily.Graphik,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    color = AppTheme.colors.contrast,
                                )
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Composable
fun HomeRecommendationItem(
    modifier: Modifier = Modifier,
    data: FilmCollection,
    onAction: (Action) -> Unit,
) {
    var expandedMovieIndex by remember { mutableIntStateOf(-1) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = data.collection?.title.orEmpty(),
                fontSize = 18.sp,
                fontFamily = FontFamily.Graphik,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.colors.contrast,
            )
            Text(
                modifier = Modifier
                    .padding(end = 26.dp)
                    .clickable(
                        interactionSource = defaultMutableInteractionSource(),
                        indication = ripple(),
                        onClick = {
                            data.collection?.let {
                                onAction.invoke(
                                    HomeViewModel.HomeAction.NavigateToCollectionScreen(collection = it)
                                )
                            }
                        }
                    ),
                text = "Все",
                color = AppTheme.colors.accent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(data.items) { index, film ->
                MovingMovieItem(
                    film = film,
                    isExpanded = expandedMovieIndex == index,
                    onExpand = {
                        expandedMovieIndex = if (expandedMovieIndex == index) -1 else index
                    },
                    onNavigateToDetails = {
                        onAction.invoke(HomeViewModel.HomeAction.NavigateToFilmDetailsScreen(film.kinopoiskId))
                    }
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(top = 51.5.dp)
                        .height(156.dp)
                        .width(111.dp)
                        .clickable(
                            interactionSource = defaultMutableInteractionSource(),
                            indication = ripple(),
                            onClick = {
                                data.collection?.let {
                                    onAction.invoke(
                                        HomeViewModel.HomeAction.NavigateToCollectionScreen(
                                            collection = it
                                        )
                                    )
                                }
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 39.5.dp)
                            .background(
                                color = AppTheme.colors.white,
                            )
                            .border(
                                width = 2.dp,
                                color = AppTheme.colors.imagePlaceholder,
                                shape = CircleShape
                            )
                    ) {
                        Image(
                            modifier = Modifier.align(Alignment.Center),
                            painter = painterResource(R.drawable.icons_arrow_right),
                            contentDescription = null,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp, start = 14.dp),
                        text = "Показать все",
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Graphik,
                        fontSize = 12.sp,
                        color = AppTheme.colors.contrast,
                    )
                }
            }
        }
    }
}

@Composable
fun MovingMovieItem(
    modifier: Modifier = Modifier,
    film: Film,
    isExpanded: Boolean,
    onExpand: (Boolean) -> Unit,
    onNavigateToDetails: () -> Unit,
) {
    val boxHeight by animateDpAsState(if (isExpanded) 200.dp else 156.dp, label = "animatedHeight")
    val boxWidth by animateDpAsState(if (isExpanded) 140.dp else 111.dp, label = "animatedWidth")

    Column(
        modifier = modifier.clickable {
            if (isExpanded) {
                onNavigateToDetails()
            } else {
                onExpand(true)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .height(boxHeight)
                .width(boxWidth)
                .background(AppTheme.colors.imagePlaceholder)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(film.posterUrl),
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .background(
                        color = AppTheme.colors.accent,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    text = film.ratingKinopoisk.toString(),
                    fontSize = 6.sp,
                    fontFamily = FontFamily.Graphik,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.white,
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .widthIn(max = 111.dp),
            text = film.nameRu,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Graphik,
            fontSize = 14.sp,
            color = AppTheme.colors.contrast,
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = film.genres.first().genre,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Graphik,
            fontSize = 12.sp,
            color = AppTheme.colors.hint,
        )
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    film: Film,
    onAction: (Action) -> Unit
) {
    Column(
        modifier = modifier
            .requiredWidth(111.dp)
            .clickable(
                interactionSource = defaultMutableInteractionSource(),
                indication = ripple(),
                onClick = {
                    onAction.invoke(
                        CollectionViewModel.CollectionAction.NavigateToFilmDetailsScreen(
                            film.kinopoiskId
                        )
                    )
                }
            ),
    ) {
        Box(
            modifier = Modifier
                .height(156.dp)
                .width(111.dp)
                .background(AppTheme.colors.imagePlaceholder)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(film.posterUrl),
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .background(
                        color = AppTheme.colors.accent,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    text = film.ratingKinopoisk.toString(),
                    fontSize = 6.sp,
                    fontFamily = FontFamily.Graphik,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.white,
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .widthIn(max = 111.dp),
            text = film.nameRu,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Graphik,
            fontSize = 14.sp,
            color = AppTheme.colors.contrast,
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = film.genres.first().genre,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Graphik,
            fontSize = 12.sp,
            color = AppTheme.colors.hint,
        )
    }
}

@Composable
fun HomeRecommendationLoadingItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 46.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Shimmer(
                modifier = Modifier
                    .height(20.dp)
                    .width(100.dp),
            )
            Shimmer(
                modifier = Modifier
                    .height(25.dp)
                    .width(24.dp),
            )
        }
        LazyRow(
            modifier = Modifier.padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(8) {
                MovieLoadingItem()
            }
        }
    }
}

@Composable
fun MovieLoadingItem() {
    Column {
        Shimmer(
            modifier = Modifier
                .height(156.dp)
                .width(111.dp)
        )
        Shimmer(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(15.dp)
                .width(57.dp),
        )
        Shimmer(
            modifier = Modifier
                .padding(top = 2.dp)
                .height(13.dp)
                .width(37.dp),
        )
    }
}