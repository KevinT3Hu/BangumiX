package cc.spie.bangumix.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import cc.spie.bangumix.R
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.ui.components.subject.RankingSubjectItem
import cc.spie.bangumix.ui.viewmodels.RankingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingComponent(viewModel: RankingViewModel) {

    val scope = rememberCoroutineScope()

    val tabs = remember { SubjectType.entries }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${stringResource(R.string.route_ranking)}/${stringResource(id = tabs[selectedTabIndex].label)}"
                    )
                }
            )
        }
    ) { innerPadding ->

        val pagerState = rememberPagerState {
            SubjectType.entries.size
        }


        val context = LocalContext.current

        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                selectedTabIndex = it
                viewModel.requestLoadPage(tabs[selectedTabIndex], 0, context)
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets(top = 0, bottom = 0))
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.fastForEachIndexed { index, entry ->
                    Tab(selected = index == selectedTabIndex, onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                        Icon(imageVector = entry.getIcon(), contentDescription = null)
                    }
                }
            }

            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->

                var pageOffset by remember { mutableIntStateOf(0) }

                val type = tabs[page]

                LaunchedEffect(key1 = pageOffset) {
                    viewModel.requestLoadPage(type, pageOffset, context)
                }

                when (viewModel.loadingState[type]!!) {
                    RankingViewModel.PageLoadingState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    RankingViewModel.PageLoadingState.Failed -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = "")
                            Button(onClick = {
                                scope.launch {
                                    viewModel.requestLoadPage(type, pageOffset, context)
                                }
                            }) {
                                Text(text = "Retry")
                            }
                        }
                    }

                    RankingViewModel.PageLoadingState.Loaded -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            viewModel.rankingSubjects[type]!![pageOffset]
                                ?.let { subjects ->
                                    subjects.forEach { subject ->
                                        RankingSubjectItem(
                                            subject = subject, modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                        )
                                        HorizontalDivider(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { pageOffset-- },
                                    enabled = pageOffset > 0
                                ) {
                                    Text(text = "Previous")
                                }
                                Text(text = "Page: $pageOffset")
                                Button(onClick = { pageOffset++ }) {
                                    Text(text = "Next")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}