package cc.spie.bangumix.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cc.spie.bangumix.data.dto.SearchRequest
import cc.spie.bangumix.data.models.SearchItem
import cc.spie.bangumix.data.repositories.BangumiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bangumiRepository: BangumiRepository
) : ViewModel() {

    private val mSearchData = MutableStateFlow<PagingData<SearchItem>>(PagingData.empty())
    val searchData get() = mSearchData

    val sort = mutableStateOf(SearchRequest.Sort.MATCH)
    val filter = mutableStateOf(SearchRequest.Filter())

    suspend fun initSearch(keyword: String) {
        mSearchData.value = PagingData.empty()
        bangumiRepository.initSearch(
            keyword,
            sort.value,
            filter.value
        ).cachedIn(viewModelScope).collect {
            mSearchData.value = it
        }
    }
}