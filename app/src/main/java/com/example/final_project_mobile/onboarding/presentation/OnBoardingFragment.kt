package com.example.final_project_mobile.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.final_project_mobile.R
import com.example.final_project_mobile.main.BottomNavScreens
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme
import com.example.final_project_mobile.ui.theme.AppTheme

class OnBoardingFragment : FlowItemFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme { OnboardingContent(
        onSkipClicked = { router.startFlow(BottomNavScreens.HomeFlowScreen) }
    ) }

}

data class OnboardingPageData(val imageRes: Int, val title: String)

@Composable
fun OnboardingContent(
    onSkipClicked: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.white)
            .padding(start = 26.dp, end = 26.dp, top = 38.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Skillcinema",fontSize = 18.sp,fontWeight = FontWeight(500))

            TextButton(
                onClick = onSkipClicked,
            ) {
                Text(text = "Пропустить", fontSize = 14.sp, color = Color.Gray)
            }
        }

        val onboardingPages = listOf(
            OnboardingPageData(R.drawable.image1, "Узнавай\nо премьерах"),
            OnboardingPageData(R.drawable.image2, "Создавай\nколлекции"),
            OnboardingPageData(R.drawable.image3, "Делись\nс друзьями")
        )

        Spacer(modifier = Modifier.padding(top = 166.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            OnboardingPage(pageData = onboardingPages[page])
        }

        Spacer(modifier = Modifier.padding(top = 56.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.Black else Color.LightGray
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .padding(2.dp)
                        .background(color = color, shape = CircleShape)
                )
            }
        }
    }
}


@Preview
@Composable
fun OnBoardingPreview(){
    OnboardingContent(
        onSkipClicked = {},
    )
}

@Composable
fun OnboardingPage(pageData: OnboardingPageData) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = pageData.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.padding(top = 67.dp))

        Text(
            text = pageData.title,
            fontSize = 32.sp
        )
    }
}
