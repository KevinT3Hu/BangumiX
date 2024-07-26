package cc.spie.bangumix.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import cc.spie.bangumix.data.models.CharacterDetail
import cc.spie.bangumix.data.models.CharacterPerson
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.repositories.BangumiRepository
import cc.spie.bangumix.utils.informError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val bangumiRepository: BangumiRepository
) : ViewModel() {

    val character = mutableStateOf<CharacterDetail?>(null)

    val characterPersons = MutableStateFlow(emptyList<CharacterPerson>())

    val loading = mutableStateOf(false)

    suspend fun initCharacter(characterId: Int, context: Context): Boolean {
        loading.value = true
        val character = bangumiRepository.getCharacterDetail(characterId)

        loading.value = false
        return if (character.isSuccess) {
            this.character.value = character.getOrNull()
            true
        } else {
            Log.e("CharacterViewModel", "initCharacter: ${character.exceptionOrNull()}")
            context.informError(character.exceptionOrNull())
            false
        }
    }

    suspend fun initCharacterPersons(characterId: Int, context: Context): Boolean {
        loading.value = true
        val characterPerson = bangumiRepository.getCharacterPersons(characterId)

        loading.value = false
        return if (characterPerson.isSuccess) {
            this.characterPersons.value = characterPerson.getOrNull()!!
            true
        } else {
            Log.e(
                "CharacterViewModel",
                "initCharacterPersons: ${characterPerson.exceptionOrNull()}"
            )
            context.informError(characterPerson.exceptionOrNull())
            false
        }
    }

    suspend fun getSubject(subjectId: Int): Subject? {
        return bangumiRepository.getSubject(subjectId).getOrNull()
    }
}