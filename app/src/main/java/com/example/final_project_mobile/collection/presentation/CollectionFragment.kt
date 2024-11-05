package com.example.final_project_mobile.collection.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.final_project_mobile.R
import com.example.final_project_mobile.compose.defaultMutableInteractionSource
import com.example.final_project_mobile.core.Action
import com.example.final_project_mobile.home.presentation.CollectionKey
import com.example.final_project_mobile.home.presentation.MovieItem
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.navigation.ScreenArgs
import com.example.final_project_mobile.navigation.getFragmentArgs
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme
import com.example.final_project_mobile.ui.theme.AppTheme
import com.example.final_project_mobile.ui.theme.Graphik
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CollectionFragment : FlowItemFragment() {

    @Parcelize
    data class Args(
        val collection: CollectionKey,
    ) : ScreenArgs()

    private val args: Args by getFragmentArgs<Args>()
    private val viewModel: CollectionViewModel by viewModel {
        parametersOf(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme {
        CollectionScreen(onAction = viewModel::dispatch)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CollectionScreen(
        onAction: (Action) -> Unit,
    ) {
        val films = viewModel.films.collectAsLazyPagingItems()
        val isFilmsEmpty = films.itemCount == 0

        val isRefreshing by remember(films) {
            derivedStateOf { films.loadState.refresh is LoadState.Loading }
        }
        val isLoadingError by remember(films) {
            derivedStateOf { films.loadState.refresh is LoadState.Error }
        }

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.white)
                .padding(
                    top = 16.dp,
                    start = 26.dp,
                    end = 26.dp,
                ),
            isRefreshing = isRefreshing && !isFilmsEmpty,
            onRefresh = films::refresh,
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = defaultMutableInteractionSource(),
                                indication = ripple(),
                                onClick = { onAction.invoke(CollectionViewModel.CollectionAction.OnBackPressed) },
                            ),
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp),
                        text = args.collection.title,
                        textAlign = TextAlign.Center,
                        color = AppTheme.colors.contrast,
                        fontFamily = FontFamily.Graphik,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                    )
                }
                if (isLoadingError) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Error",
                            color = AppTheme.colors.contrast,
                            fontFamily = FontFamily.Graphik,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                        )
                    }
                }
                LazyVerticalGrid(
                    modifier = Modifier.padding(
                        top = 28.dp,
                        start = 34.dp,
                        end = 34.dp,
                    ),
                    columns = GridCells.Fixed(2),
                ) {
                    items(
                        count = films.itemCount,
                    ) { index ->
                        val item = films[index] ?: return@items
                        MovieItem(
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            ),
                            film = item,
                            onAction = onAction,
                        )
                    }
                }
            }
        }
    }
}