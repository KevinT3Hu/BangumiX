package cc.spie.bangumix.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.ui.components.subject.SearchSubjectItem
import cc.spie.bangumix.ui.theme.BangumiXTheme
import cc.spie.bangumix.ui.viewmodels.SearchViewModel
import cc.spie.bangumix.utils.preferences.SearchHistoryManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {

    @Inject
    lateinit var searchHistoryManager: SearchHistoryManager

    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
        ExperimentalFoundationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SearchViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            BangumiXTheme {

                var searchKeyword by rememberSaveable {
                    mutableStateOf("")
                }

                var expanded by rememberSaveable {
                    mutableStateOf(false)
                }

                var filterExpanded by rememberSaveable {
                    mutableStateOf(false)
                }

                var filterChanged by rememberSaveable {
                    mutableStateOf(false)
                }

                var historyToRemove by rememberSaveable {
                    mutableStateOf<String?>(null)
                }

                var showAskRemoveDialog by rememberSaveable {
                    mutableStateOf(false)
                }

                val scope = rememberCoroutineScope()

                LaunchedEffect(key1 = filterExpanded) {
                    // Reset search when filter is changed
                    if (!filterExpanded && filterChanged) {
                        viewModel.initSearch(searchKeyword)
                        filterChanged = false
                    }
                }

                val history = searchHistoryManager.searchHistoryFlow.collectAsState()
                val searchData = viewModel.searchData.collectAsLazyPagingItems()
                Column(modifier = Modifier.fillMaxWidth()) {
                    SearchBar(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = searchKeyword,
                                onQueryChange = { searchKeyword = it },
                                onSearch = {
                                    scope.launch {
                                        searchHistoryManager.addSearchHistory(searchKeyword)
                                        viewModel.initSearch(searchKeyword)
                                    }
                                },
                                expanded = expanded,
                                onExpandedChange = { expanded = it },
                                leadingIcon = {
                                    IconButton(onClick = {
                                        if (expanded) {
                                            expanded = false
                                        } else {
                                            finish()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        filterExpanded = true
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        },
                        expanded = expanded,
                        onExpandedChange = {
                            if (!it) {
                                searchKeyword = ""
                            }
                            expanded = it
                        }) {
                        if (!searchData.loadState.isIdle) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }

                        LazyColumn {
                            items(searchData.itemCount, contentType = { 0 }) { idx ->
                                SearchSubjectItem(item = searchData[idx]!!)
                            }
                            if (searchData.loadState.hasError) {
                                item(contentType = { 1 }) {
                                    Button(onClick = { searchData.retry() }) {
                                        Text(text = "Retry")
                                    }
                                }
                            }
                        }
                    }

                    if (!expanded) {
                        HorizontalDivider()
                        LazyColumn {
                            items(history.value) { keyword ->
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = {
                                            searchKeyword = keyword
                                            expanded = true
                                            scope.launch {
                                                searchHistoryManager.addSearchHistory(keyword)
                                                viewModel.initSearch(keyword)
                                            }
                                        },
                                        onLongClick = {
                                            historyToRemove = keyword
                                            showAskRemoveDialog = true
                                        }
                                    )) {
                                    Text(
                                        text = keyword, modifier = Modifier
                                            .padding(vertical = 12.dp)
                                            .padding(start = 16.dp)
                                    )
                                }
                                HorizontalDivider()
                            }
                        }
                    }

                    if (filterExpanded) {
                        ModalBottomSheet(onDismissRequest = { filterExpanded = false }) {
                            Text(
                                text = "Filter",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 4.dp)
                            )

                            // Type filter
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Type",
                                    style = MaterialTheme.typography.titleSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                FlowRow {
                                    SubjectType.entries.forEach { type ->
                                        val selected = viewModel.filter.value.type.contains(type)
                                        FilterChip(
                                            modifier = Modifier.padding(end = 8.dp),
                                            selected = selected,
                                            onClick = {
                                                filterChanged = true
                                                if (viewModel.filter.value.type.contains(type)) {
                                                    viewModel.filter.value =
                                                        viewModel.filter.value.copy(type = viewModel.filter.value.type - type)
                                                } else {
                                                    viewModel.filter.value =
                                                        viewModel.filter.value.copy(type = viewModel.filter.value.type + type)
                                                }
                                            },
                                            leadingIcon = {
                                                if (selected) {
                                                    Icon(
                                                        imageVector = Icons.Default.Done,
                                                        contentDescription = null
                                                    )
                                                }
                                            },

                                            label = { Text(text = "$type") })
                                    }
                                }
                            }
                        }
                    }

                    if (showAskRemoveDialog) {
                        AlertDialog(
                            onDismissRequest = { showAskRemoveDialog = false },
                            confirmButton = {
                                Button(onClick = {
                                    scope.launch {
                                        searchHistoryManager.removeSearchHistory(historyToRemove!!)
                                    }
                                    showAskRemoveDialog = false
                                }) {
                                    Text(text = "Remove")
                                }
                            },
                            dismissButton = {
                                FilledTonalButton(onClick = { showAskRemoveDialog = false }) {
                                    Text(text = "Cancel")
                                }
                            },
                            title = {
                                Text(text = "Remove \"$historyToRemove\" from history?")
                            })
                    }
                }
            }
        }
    }
}

