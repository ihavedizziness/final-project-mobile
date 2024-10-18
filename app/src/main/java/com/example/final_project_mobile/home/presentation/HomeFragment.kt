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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.final_project_mobile.R
import com.example.final_project_mobile.home.domain.Movie
import com.example.final_project_mobile.home.domain.MovieRecommendation
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme
import com.example.final_project_mobile.ui.theme.AppTheme
import com.example.final_project_mobile.ui.theme.Graphik

class HomeFragment : FlowItemFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme { HomeContent() }


    @Composable
    fun HomeContent() {
        val dummySections = listOf("Премьеры", "Популярное", "Боевики США", "Топ 250", "Драмы Франции", "Сериалы")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 26.dp, top = 55.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                contentDescription = null ,
                painter = painterResource(R.drawable.home_title)
            )

            dummySections.forEach { section ->
                HomeRecommendationItem(
                    data = MovieRecommendation(
                        title = section,
                        recommendationList = MovieRecommendation.dummyMovies,
                    )
                )
            }
        }
    }
}


    @Composable
    fun HomeRecommendationItem(
        data: MovieRecommendation,
    ) {
        var expandedMovieIndex by remember { mutableStateOf(-1) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 46.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = data.title,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Graphik,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.colors.contrast,
                )
                Text(
                    modifier = Modifier.padding(end = 26.dp),
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
                itemsIndexed(data.recommendationList) { index, movie ->
                    HomeMovieItem(
                        movie = movie,
                        isExpanded = expandedMovieIndex == index,
                        onExpand = {
                            expandedMovieIndex = if (expandedMovieIndex == index) -1 else index
                        }
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 51.5.dp)
                            .height(156.dp)
                            .width(111.dp)
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
fun HomeMovieItem(
    movie: Movie,
    isExpanded: Boolean,
    onExpand: () -> Unit,
) {
    val boxHeight by animateDpAsState(if (isExpanded) 200.dp else 156.dp)
    val boxWidth by animateDpAsState(if (isExpanded) 140.dp else 111.dp)

    Column(
        modifier = Modifier.clickable { onExpand.invoke() },
    ) {
        Box(
            modifier = Modifier
                .height(boxHeight)
                .width(boxWidth)
                .background(AppTheme.colors.imagePlaceholder)
        ) {
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
                    text = movie.rating.toString(),
                    fontSize = 6.sp,
                    fontFamily = FontFamily.Graphik,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.colors.white,
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = movie.title,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Graphik,
            fontSize = 14.sp,
            color = AppTheme.colors.contrast,
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = movie.genre,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Graphik,
            fontSize = 12.sp,
            color = AppTheme.colors.hint,
        )
    }
}
