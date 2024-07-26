package cc.spie.bangumix.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.models.UserSubjectCollection
import cc.spie.bangumix.data.repositories.BangumiRepository
import cc.spie.bangumix.utils.LoginStateHolder
import cc.spie.bangumix.utils.informError
import cc.spie.bangumix.utils.preferences.LoginInfoManager
import cc.spie.bangumix.utils.preferences.OptionsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BangumiXViewModel @Inject constructor(
    private val bangumiRepository: BangumiRepository,
    private val loginStateHolder: LoginStateHolder,
    private val loginInfoManager: LoginInfoManager,
    private val optionsManager: OptionsManager
) : ViewModel() {

    val userInfoLoadState = mutableStateOf(UserInfoLoadState.NOT_LOGGED_IN)

    val userCollections = MutableStateFlow(PagingData.empty<UserSubjectCollection>())

    val collectionTypeFilter = mutableStateOf<CollectionType?>(null)

    private val subjectType = mutableStateOf<SubjectType?>(null)

    fun tryLogin(context: Context) {
        viewModelScope.launch {
            val info = loginInfoManager.getSavedLogin()
            if (info != null) {
                requestUserInfo(info.accessToken, context)
            }
        }
    }

    suspend fun getLastSelectedTabIndex() = optionsManager.getLastSelectedIndex()

    private fun requestUserInfo(accessToken: String, context: Context) {
        userInfoLoadState.value = UserInfoLoadState.LOADING
        viewModelScope.launch {
            val userInfo = bangumiRepository.getLoggedInUserInfo(accessToken)
            if (userInfo.isSuccess) {
                val info = userInfo.getOrNull()!!
                loginStateHolder.recordLogin(info, accessToken)
                Log.d("BangumiXViewModel", "Logged in as ${info.username}")
                userInfoLoadState.value = UserInfoLoadState.LOADED
                bangumiRepository.getUserCollections().cachedIn(viewModelScope).collect {
                    Log.d("BangumiXViewModel", "User collections loaded")
                    userCollections.value = it
                }
            } else {
                val e = userInfo.exceptionOrNull()
                context.informError(e)
                Log.e("BangumiXViewModel", "Failed to get user info", e)
                userInfoLoadState.value = UserInfoLoadState.NOT_LOGGED_IN
            }
        }
    }

    private fun requestNewCollections() {
        userCollections.value = PagingData.empty()
        viewModelScope.launch {
            bangumiRepository.getUserCollections(subjectType.value, collectionTypeFilter.value)
                .cachedIn(viewModelScope).collect {
                    userCollections.value = it
                }
        }
    }

    suspend fun getSubject(id: Int) = bangumiRepository.getSubject(id)

    fun toggleCollectionType(type: CollectionType?) {
        collectionTypeFilter.value = if (collectionTypeFilter.value == type) null else type
        requestNewCollections()
    }

    fun toggleSubjectType(type: SubjectType?) {
        subjectType.value = type
        requestNewCollections()
    }

    enum class UserInfoLoadState {
        LOADING,
        NOT_LOGGED_IN,
        LOADED
    }
}