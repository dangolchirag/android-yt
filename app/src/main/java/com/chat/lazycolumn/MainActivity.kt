package com.chat.lazycolumn

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.chat.lazycolumn.ui.theme.LazyColumnTheme
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf


const val DURATION_IN_MILLIS = 1000

val items = listOf(
    ListItem(
        imageRes = R.drawable.sun, title = "Sun", description = "Star of our solar system."
    ), ListItem(
        imageRes = R.drawable.mercury, title = "Mercury", description = "Closest to the sun."
    ), ListItem(
        imageRes = R.drawable.venus, title = "Venus", description = "Second planet from the sun."
    ), ListItem(
        imageRes = R.drawable.earth, title = "Earth", description = "Third planet from the sun"
    ), ListItem(
        imageRes = R.drawable.mars, title = "Mars", description = "Fourth planet from the sun."
    ), ListItem(
        imageRes = R.drawable.jupiter, title = "Jupiter", description = "Fifth planet from the sun."
    ), ListItem(
        imageRes = R.drawable.saturn, title = "Saturn", description = "Sixth planet from the sun."
    ), ListItem(
        imageRes = R.drawable.uranus, title = "Uranus", description = "Seventh planet from the sun."
    ), ListItem(
        imageRes = R.drawable.neptune,
        title = "Neptune",
        description = "Eighth planet from the sun."
    ), ListItem(
        imageRes = R.drawable.pluto,
        title = "Pluto",
        description = "Dwarf planet in the Kuiper Belt."
    )
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumnTheme {
                AppScreen()
            }
        }
    }
}
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    SharedTransitionLayout(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = PlanetScreen.PlanetScreenList,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<PlanetScreen.PlanetScreenList> {
                PlanetList(onItemClick = {
                    navController.navigate(PlanetScreen.PlanetScreenDetail(it))
                }, animatedVisibilityScope = this)
            }

            composable<PlanetScreen.PlanetScreenDetail>(
                typeMap = mapOf(
                    typeOf<ListItem>() to CustomNavType(ListItem::class.java,ListItem.serializer())
                )
            ) {
                val item = it.toRoute<PlanetScreen.PlanetScreenDetail>().item
                PlanetDetail(item = item, onBackClick = {
                    navController.popBackStack()
                }, animatedVisibilityScope = this)
            }

        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.PlanetList(
    onItemClick: (ListItem) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Solar System", style = MaterialTheme.typography.titleLarge
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }) { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(items, key = { it.title }) { item ->
                ItemScreen(item, onItemClick, animatedVisibilityScope)
            }
        }
    }
}



@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemScreen(
    item: ListItem,
    onItemClick: (ListItem) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            onItemClick(item)
        }) {

        Row {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(8.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${item.imageRes}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION_IN_MILLIS)
                        }
                    )
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = "text/${item.title}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION_IN_MILLIS)
                        }
                    )
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = "text/${item.description}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION_IN_MILLIS)
                        }
                    )
                )
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PlanetDetail(
    item: ListItem,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
            title = {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(key = "text/${item.title}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION_IN_MILLIS)
                        }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/${item.imageRes}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION_IN_MILLIS)
                        }
                    )

            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "text/${item.description}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = DURATION_IN_MILLIS)
                        }
                    ),
                textAlign = TextAlign.Center,
                text = item.description,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
            )
        }
    }
}


@Serializable
@Parcelize
data class ListItem(
    val imageRes: Int, val title: String, val description: String
) : Parcelable



val ScreenInfoNavType = object : NavType<ListItem>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ListItem? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, ListItem::class.java)
        } else {
            @Suppress("DEPRECATION") // for backwards compatibility
            bundle.getParcelable(key)
        }


    override fun put(bundle: Bundle, key: String, value: ListItem) =
        bundle.putParcelable(key, value)

    override fun parseValue(value: String): ListItem = Json.decodeFromString(value)

    override fun serializeAsValue(value: ListItem): String = Json.encodeToString(value)

    override val name: String = "ScreenInfo"

}

class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz) as T
        } else {
            @Suppress("DEPRECATION") // for backwards compatibility
            bundle.getParcelable(key)
        }

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)

    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)

    override val name: String = clazz.name
}


