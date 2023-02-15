package com.praveen.astro.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.praveen.astro.misc.AstroDetailTag
import com.praveen.astro.ui.common.ScreenTitle
import com.praveen.astro.ui.theme.FontLato
import com.praveen.astro.utils.AstroScreen
import com.praveen.astro.viewModels.AstrosViewModel

@Composable
fun AstroDetail(
    astroName: String,
    astrosViewModel: AstrosViewModel,
) {
    astrosViewModel.loadAstroWithName(astroName)
    val details by astrosViewModel.astroWithNameFlow.collectAsState()
    val scrollState = rememberScrollState(0)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .testTag(AstroDetailTag)
            .verticalScroll(
                scrollState,
            ),
    ) {
        val (title, astroImage, name, astroDetails) = createRefs()

        ScreenTitle(
            title = AstroScreen.Details.title,
            modifier = Modifier
                .padding(20.dp)
                .constrainAs(title) {},
        )

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(astroImage) {
                    top.linkTo(title.bottom)
                    centerHorizontallyTo(parent)
                },
        ) {
            Image(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(300.dp),
                painter = rememberImagePainter(data = details.personImageUrl),
                contentDescription = details.name,
                contentScale = ContentScale.FillHeight,
            )
        }

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(8.dp)
                .constrainAs(name) {
                    top.linkTo(astroImage.bottom)
                    centerHorizontallyTo(astroImage)
                },
            text = details.name,
            style = MaterialTheme.typography.h4,
        )

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(16.dp)
                .constrainAs(astroDetails) {
                    top.linkTo(name.bottom)
                    centerHorizontallyTo(astroImage)
                },
            text = details.personBio,
            style = TextStyle(
                fontFamily = FontLato,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = Color.Gray,
            ),
        )
    }
}
