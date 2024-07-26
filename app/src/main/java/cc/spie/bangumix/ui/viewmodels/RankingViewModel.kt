package cc.spie.bangumix.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.toMutableStateMap
import androidx.lifecycle.ViewModel
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.repositories.BangumiRepository
import cc.spie.bangumix.data.webparsers.Ranking
import cc.spie.bangumix.utils.informError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val bangumiRepository: BangumiRepository
) : ViewModel() {

    val loadingState = SubjectType.entries.map {
        it to PageLoadingState.Loading
    }.toMutableStateMap()

    private val mRankings: MutableMap<SubjectType, Ranking> = mutableMapOf()
    val rankingSubjects = mutableStateMapOf(
        SubjectType.Anime to mutableStateMapOf<Int, List<Subject>>(),
        SubjectType.Book to mutableStateMapOf(),
        SubjectType.Music to mutableStateMapOf(),
        SubjectType.Game to mutableStateMapOf(),
        SubjectType.Real to mutableStateMapOf()
    )

    private fun requestRanking(type: SubjectType): Ranking {
        return mRankings[type] ?: Ranking(type, bangumiRepository).also {
            mRankings[type] = it
        }
    }

    suspend fun requestLoadPage(type: SubjectType, page: Int, context: Context) {
        loadingState[type] = PageLoadingState.Loading
        if ((rankingSubjects[type]?.get(page)) != null) {
            loadingState[type] = PageLoadingState.Loaded
            return
        }
        val ranking = requestRanking(type)
        val result = ranking.requestPage(page)
        if (result.isFailure) {
            context.informError(result.exceptionOrNull())
            loadingState[type] = PageLoadingState.Failed
        } else {
            rankingSubjects[type]!![page] = result.getOrNull()!!
            loadingState[type] = PageLoadingState.Loaded
        }
    }

    enum class PageLoadingState {
        Loading,
        Loaded,
        Failed
    }
}