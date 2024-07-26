package cc.spie.bangumix.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import cc.spie.bangumix.data.apis.BangumiApi
import cc.spie.bangumix.data.dto.SearchRequest
import cc.spie.bangumix.data.models.SearchItem

private const val TAG = "SubjectSearchPagingSource"

class SubjectSearchPagingSource(
    private val api: BangumiApi,
    private val keyword: String,
    private val sort: SearchRequest.Sort,
    private val filter: SearchRequest.Filter
) : PagingSource<Int, SearchItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val offset = params.key ?: 0
        return try {
            val response = api.search(SearchRequest(keyword, sort, filter), params.loadSize, offset)
            LoadResult.Page(
                data = response.data,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (response.data.isEmpty()) null else offset + response.data.size
            )
        } catch (e: Exception) {
            Log.e(TAG, "load: ", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}