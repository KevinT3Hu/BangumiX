package cc.spie.bangumix.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import cc.spie.bangumix.data.apis.BangumiApi
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.models.UserSubjectCollection
import retrofit2.HttpException

private const val TAG = "UserCollectionPagingSource"

class UserCollectionPagingSource(
    private val api: BangumiApi,
    private val userName: String,
    private val accessToken: String? = null,
    private val subjectType: SubjectType? = null,
    private val collectionType: CollectionType? = null
) : PagingSource<Int, UserSubjectCollection>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserSubjectCollection> {
        Log.d(TAG, "load: ${params.key}")
        val offset = params.key ?: 0
        return try {
            val response = api.getUserCollections(
                userName,
                params.loadSize,
                offset,
                accessToken = accessToken,
                subjectType = subjectType,
                type = collectionType
            )
            Log.d(TAG, "load: ${response.data.size} data")
            LoadResult.Page(
                data = response.data,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (response.data.isEmpty()) null else offset + response.data.size
            )
        } catch (e: HttpException) {
            Log.e(TAG, "load: ", e)
            Log.e(TAG, "load: ${e.response()?.raw()}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "load: ", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserSubjectCollection>): Int? {
        return null
    }
}