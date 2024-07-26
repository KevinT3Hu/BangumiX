package cc.spie.bangumix.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cc.spie.bangumix.R
import cc.spie.bangumix.ui.routes.FavoritesComponent
import cc.spie.bangumix.ui.routes.RankingComponent
import cc.spie.bangumix.ui.theme.BangumiXTheme
import cc.spie.bangumix.ui.viewmodels.BangumiXViewModel
import cc.spie.bangumix.ui.viewmodels.RankingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: BangumiXViewModel by viewModels()

        viewModel.tryLogin(this)

        enableEdgeToEdge()
        setContent {
            BangumiXTheme {

                val navController = rememberNavController()

                val routes = listOf(Route.Favorites, Route.Ranking)

                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        routes.forEach { route ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == route.route } == true,
                                onClick = {
                                    navController.navigate(route.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true

                                    }
                                },
                                label = { Text(text = stringResource(id = route.label)) },
                                icon = {
                                    Icon(imageVector = route.icon, contentDescription = null)
                                })
                        }
                    }
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Favorites.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        routes.forEach { route ->
                            composable(
                                route.route,
                                enterTransition = {
                                    val initialIndex =
                                        routes.indexOfFirst { it.route == navController.previousBackStackEntry?.destination?.route }
                                    val targetIndex =
                                        routes.indexOfFirst { it.route == navController.currentBackStackEntry?.destination?.route }
                                    if (initialIndex < targetIndex) {
                                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
                                    } else {
                                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
                                    }
                                },
                                exitTransition = {
                                    val initialIndex =
                                        routes.indexOfFirst { it.route == navController.previousBackStackEntry?.destination?.route }
                                    val targetIndex =
                                        routes.indexOfFirst { it.route == navController.currentBackStackEntry?.destination?.route }
                                    if (initialIndex < targetIndex) {
                                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start)
                                    } else {
                                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
                                    }
                                }
                            ) {
                                when (route.route) {
                                    Route.Favorites.route -> route.component(viewModel)
                                    Route.Ranking.route -> {
                                        val rankingViewModel: RankingViewModel = hiltViewModel()
                                        route.component(rankingViewModel)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class Route(
        val route: String,
        val icon: ImageVector,
        @StringRes val label: Int,
        val component: @Composable (ViewModel) -> Unit
    ) {
        data object Favorites : Route(
            "fav",
            Icons.Default.Favorite,
            R.string.route_favorites,
            { FavoritesComponent(it as BangumiXViewModel) })

        data object Ranking : Route("rak",
            Icons.AutoMirrored.Filled.List,
            R.string.route_ranking,
            { RankingComponent(it as RankingViewModel) })
    }
}