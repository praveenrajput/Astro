package com.praveen.astro.ui.astros

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.praveen.astro.models.People
import com.praveen.astro.ui.theme.FontLato
import com.praveen.astro.ui.theme.LightGrey
import com.praveen.astro.ui.theme.cardBackground

@Composable
fun AstroItem(people: People) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(cardBackground)
    ) {
        val (astroImage, astroName, spaceCraft) = createRefs()
        createVerticalChain(astroName, spaceCraft, chainStyle = ChainStyle.Packed(4F))
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(astroImage) {}
        ) {
            Image(
                painter = rememberImagePainter(people.personImageUrl),
                contentDescription = "Astro",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .absolutePadding(left = 10.dp)
                .constrainAs(astroName) {
                    start.linkTo(astroImage.end)
                    centerVerticallyTo(astroImage)
                }
        ) {
            Text(
                text = people.name,
                style = MaterialTheme.typography.h6
            )
        }

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .absolutePadding(
                    top = 4.dp,
                    left = 10.dp
                )
                .constrainAs(spaceCraft) {
                    start.linkTo(astroName.start)
                    centerVerticallyTo(astroImage)
                }
        ) {
            Text(
                text = people.craft,
                style = TextStyle(
                    fontFamily = FontLato,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = LightGrey
                )
            )
        }
    }
}
