package cc.spie.bangumix.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cc.spie.bangumix.data.models.PersonDetail
import cc.spie.bangumix.data.repositories.BangumiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val bangumiRepository: BangumiRepository
) : ViewModel() {

    val person = mutableStateOf<PersonDetail?>(null)

    val loading = mutableStateOf(false)

    suspend fun initPerson(personId: Int): Boolean {
        loading.value = true
        val result = bangumiRepository.getPersonDetail(personId)
        loading.value = false
        return if (result.isSuccess) {
            person.value = result.getOrNull()
            true
        } else {
            Log.e("PersonViewModel", "initPerson: ${result.exceptionOrNull()}")
            false
        }
    }
}