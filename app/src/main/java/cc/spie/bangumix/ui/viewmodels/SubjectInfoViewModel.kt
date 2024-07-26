package cc.spie.bangumix.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.Episode
import cc.spie.bangumix.data.models.EpisodeCollectionType
import cc.spie.bangumix.data.models.RelatedCharacter
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.models.UserEpisodeCollection
import cc.spie.bangumix.data.models.UserSubjectCollection
import cc.spie.bangumix.data.repositories.BangumiRepository
import cc.spie.bangumix.utils.LoginStateHolder
import cc.spie.bangumix.utils.informError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SubjectInfoViewModel"

@HiltViewModel
class SubjectInfoViewModel @Inject constructor(
    private val bangumiRepository: BangumiRepository,
    private val loginStateHolder: LoginStateHolder
) : ViewModel() {

    val subject = mutableStateOf<Subject?>(null)

    val collection = mutableStateOf<UserSubjectCollection?>(null)

    val episodeCollections = MutableStateFlow<List<UserEpisodeCollection>>(emptyList())
    val episodes = MutableStateFlow<List<Episode>>(emptyList())

    val relatedCharacters = MutableStateFlow<List<RelatedCharacter>>(emptyList())

    val loading = mutableStateOf(false)

    suspend fun initSubject(id: Int, context: Context, userName: String? = null): Boolean {
        loading.value = true
        val subjectResult = bangumiRepository.getSubject(id)
        if (!userName.isNullOrEmpty()) {
            val collectionResult = bangumiRepository.getUserCollectionSubject(userName, id)
            if (collectionResult.isSuccess) {
                collectionResult.getOrNull()?.let {
                    collection.value = it
                }
            } else {
                val e = collectionResult.exceptionOrNull()
                Log.e(TAG, "Failed to get user collection", e)
                context.informError(e) { httpError ->
                    if (httpError.code() == 404) {
                        collection.value = null
                        return@informError true
                    }
                    false
                }
            }
        }
        loading.value = false
        return if (subjectResult.isSuccess) {
            subjectResult.getOrNull()?.let {
                subject.value = it
            }
            true
        } else {
            val e = subjectResult.exceptionOrNull()
            Log.e(TAG, "Failed to get subject", e)
            context.informError(e)
            false
        }
    }

    suspend fun modifyCollectionType(context: Context, type: CollectionType) {
        val subject = subject.value ?: return
        val accessToken = loginStateHolder.accessToken.value ?: return
        val result = bangumiRepository.modifyCollectionType(subject.id, type, accessToken)
        if (result.isSuccess) {
            if (collection.value != null) {
                collection.value = collection.value!!.copy(type = type)
            } else {
                val updatedCollection = bangumiRepository.getUserCollectionSubject(
                    loginStateHolder.loggedUser.value!!.username,
                    subject.id
                )
                if (updatedCollection.isSuccess) {
                    collection.value = updatedCollection.getOrNull()
                } else {
                    val e = updatedCollection.exceptionOrNull()
                    Log.e(TAG, "Failed to get user collection", e)
                    context.informError(e)
                }
            }
        } else {
            val e = result.exceptionOrNull()
            Log.e(TAG, "Failed to modify collection", e)
            context.informError(e)
        }
    }

    fun requestEpisodeCollections(context: Context) {
        viewModelScope.launch {
            val subject = subject.value ?: return@launch
            val result = bangumiRepository.getEpisodeCollections(subject.id)
            if (result.isSuccess) {
                episodeCollections.value = result.getOrNull()!!
            } else {
                val e = result.exceptionOrNull()
                Log.e(TAG, "Failed to get episode collections", e)
                context.informError(e)
            }
        }
    }

    fun requestEpisodes(context: Context) {
        viewModelScope.launch {
            val subject = subject.value ?: return@launch
            val result = bangumiRepository.getEpisodes(subject.id)
            if (result.isSuccess) {
                episodes.value = result.getOrNull()!!
            } else {
                val e = result.exceptionOrNull()
                Log.e(TAG, "Failed to get episodes", e)
                context.informError(e)
            }
        }
    }

    suspend fun modifyEpisodeCollectionType(
        episodeId: Int,
        type: EpisodeCollectionType,
        context: Context
    ): Boolean {
        val subject = subject.value ?: return false
        val accessToken = loginStateHolder.accessToken.value ?: return false
        val result =
            bangumiRepository.modifyEpisodeCollectionType(subject.id, episodeId, type, accessToken)
        if (result.isSuccess) {
            return true
        } else {
            val e = result.exceptionOrNull()
            Log.e(TAG, "Failed to modify episode collection", e)
            context.informError(e)
            return false
        }
    }

    fun requestRelatedCharacters(context: Context) {
        viewModelScope.launch {
            val subject = subject.value ?: return@launch
            val result = bangumiRepository.getSubjectCharacters(subject.id)
            if (result.isSuccess) {
                relatedCharacters.value = result.getOrNull()!!
            } else {
                val e = result.exceptionOrNull()
                Log.e(TAG, "Failed to get related characters", e)
                context.informError(e)
            }
        }
    }
}