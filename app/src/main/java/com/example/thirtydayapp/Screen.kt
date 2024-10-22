package com.example.thirtydayapp

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.thirtydayapp.data.DessertRepository
import com.example.thirtydayapp.model.Dessert

@Composable
fun MainCard(
    dessert: Dessert,
    modifier: Modifier = Modifier
) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 360f else 0f,
        animationSpec = tween(
            durationMillis = 600,
        ),
        label = "customRotation"
    )
    ElevatedCard(
        onClick = {
            isFlipped = !isFlipped
        },
        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_medium)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.default_elevation)
        ),
        modifier = modifier
            .graphicsLayer(
                rotationY = rotation,
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 350,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Surface (
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_medium)),
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shadowElevation = dimensionResource(R.dimen.surface_shadow_elevation),
                tonalElevation = dimensionResource(R.dimen.surface_tonal_elevation)
            ) {
                Text(
                    text = stringResource(R.string.day_text, integerResource(dessert.dayRes)),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(R.dimen.padding_small_medium),
                            vertical = dimensionResource(R.dimen.padding_small)
                        )
                )
            }
            Text(
                text = stringResource(dessert.nameRes),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_small)
                    )
            )
            if (rotation <= 90) {
                Image(
                    painter = painterResource(dessert.imageRes),
                    contentDescription = stringResource(dessert.nameRes),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.image_height))
                        .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_medium)))
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small)
                        )
                ){
                    Text(
                        text = stringResource(dessert.goalRes),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            if (rotation > 90) {
                Text(
                    text =  stringResource(dessert.descriptionRes).prependIndent(stringResource(R.string.tab).padStart(5)),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun ListOfMainCards(
    desserts: List<Dessert>,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 320.dp),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        contentPadding = PaddingValues(
            bottom = WindowInsets.safeDrawing.asPaddingValues()
                .calculateBottomPadding(),
            start = dimensionResource(R.dimen.padding_small),
            end = dimensionResource(R.dimen.padding_small),
            top = dimensionResource(R.dimen.padding_small)
        ),
        modifier = modifier,
    ) {
        items(
            items = desserts,
            key = { it.dayRes }
        ) {
            MainCard(dessert = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar () {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun App() {
    val localLayoutDir = LocalLayoutDirection.current
    Scaffold (
        topBar = { CustomTopAppBar() },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        ListOfMainCards(
            desserts = DessertRepository.getAll,
            Modifier.padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(localLayoutDir),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(localLayoutDir),
                top = innerPadding.calculateTopPadding()
            )
        )
    }
}