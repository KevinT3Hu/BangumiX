package cc.spie.bangumix.ui.routes

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.ui.activities.LoginActivity
import cc.spie.bangumix.ui.activities.SearchActivity
import cc.spie.bangumix.ui.components.collection.CollectionSubjectItem
import cc.spie.bangumix.ui.viewmodels.BangumiXViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesComponent(viewModel: BangumiXViewModel) {

    val context = LocalContext.current

    val collections = viewModel.userCollections.collectAsLazyPagingItems()

    when (viewModel.userInfoLoadState.value) {
        BangumiXViewModel.UserInfoLoadState.NOT_LOGGED_IN -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(onClick = {
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(intent)
                }, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }

        BangumiXViewModel.UserInfoLoadState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        BangumiXViewModel.UserInfoLoadState.LOADED -> {

            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(text = stringResource(R.string.collections))
                    }, actions = {

                        Box {

                            var expandMenu by remember {
                                mutableStateOf(false)
                            }

                            IconButton(onClick = { expandMenu = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.filter),
                                    contentDescription = null
                                )
                            }

                            DropdownMenu(
                                expanded = expandMenu,
                                onDismissRequest = { expandMenu = false }) {
                                CollectionType.entries.forEach { type ->
                                    DropdownMenuItem(onClick = {
                                        viewModel.toggleCollectionType(type)
                                        expandMenu = false
                                    }, leadingIcon = {
                                        if (viewModel.collectionTypeFilter.value == type) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null
                                            )
                                        }
                                    }, text = {
                                        Text(text = stringResource(id = type.label))
                                    })

                                }
                            }
                        }

                        IconButton(onClick = { collections.refresh() }) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                        }
                    }, windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
                        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        val intent = Intent(context, SearchActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            ) { innerPadding ->

                var selectedTab by remember {
                    mutableIntStateOf(0)
                }

                val scope = rememberCoroutineScope()

                LaunchedEffect(key1 = Unit) {
                    selectedTab = viewModel.getLastSelectedTabIndex()
                }

                val tabs = SubjectType.entries.toList()
                val pullRefreshState = rememberPullToRefreshState()

                LaunchedEffect(key1 = selectedTab) {
                    if (selectedTab == 0) {
                        viewModel.toggleSubjectType(null)
                    } else {
                        viewModel.toggleSubjectType(tabs[selectedTab - 1])
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .windowInsetsPadding(WindowInsets(bottom = 0))
                ) {

                    val pagerState = rememberPagerState {
                        tabs.size
                    }

                    LaunchedEffect(key1 = pagerState) {
                        snapshotFlow { pagerState.currentPage }.collect {
                            selectedTab = it
                        }
                    }

                    PrimaryScrollableTabRow(selectedTabIndex = selectedTab) {

                        Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, icon = {
                            Icon(imageVector = Icons.Default.Home, contentDescription = null)
                        }, text = {
                            Text(text = stringResource(R.string.tab_all))
                        })

                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTab == index + 1,
                                onClick = {
                                    selectedTab = index + 1
                                    scope.launch {
                                        pagerState.animateScrollToPage(selectedTab)

                                    }
                                },
                                icon = {
                                    Icon(imageVector = tab.getIcon(), contentDescription = null)
                                },
                                text = {
                                    Text(text = stringResource(id = tab.label))
                                })
                        }
                    }

                    HorizontalPager(state = pagerState) {
                        PullToRefreshBox(
                            isRefreshing = collections.loadState.refresh == LoadState.Loading,
                            state = pullRefreshState,
                            onRefresh = { collections.refresh() },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(
                                    collections.itemCount,
                                    contentType = { 0 },
                                    key = { collections[it]!!.subjectId }) { index ->
                                    val subject = remember {
                                        collections[index]!!
                                    }
                                    CollectionSubjectItem(item = subject, viewModel = viewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}