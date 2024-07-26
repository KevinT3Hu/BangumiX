package cc.spie.bangumix.data.repositories

import androidx.paging.PagingData
import cc.spie.bangumix.data.dto.SearchRequest
import cc.spie.bangumix.data.models.Calendar
import cc.spie.bangumix.data.models.CharacterDetail
import cc.spie.bangumix.data.models.CharacterPerson
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.Episode
import cc.spie.bangumix.data.models.EpisodeCollectionType
import cc.spie.bangumix.data.models.EpisodeType
import cc.spie.bangumix.data.models.PersonDetail
import cc.spie.bangumix.data.models.RelatedCharacter
import cc.spie.bangumix.data.models.RelatedPerson
import cc.spie.bangumix.data.models.SearchItem
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.models.User
import cc.spie.bangumix.data.models.UserEpisodeCollection
import cc.spie.bangumix.data.models.UserSubjectCollection
import kotlinx.coroutines.flow.Flow

interface BangumiRepository {

    suspend fun calendar(): Result<List<Calendar>>

    fun initSearch(
        keyword: String,
        sort: SearchRequest.Sort,
        filter: SearchRequest.Filter
    ): Flow<PagingData<SearchItem>>

    fun getUserCollections(
        subjectType: SubjectType? = null,
        collectionType: CollectionType? = null
    ): Flow<PagingData<UserSubjectCollection>>

    suspend fun getEpisodeCollections(
        subjectId: Int,
        episodeType: EpisodeType? = null
    ): Result<List<UserEpisodeCollection>>

    suspend fun getEpisodes(
        subjectId: Int,
        episodeType: EpisodeType? = null
    ): Result<List<Episode>>

    suspend fun getSubject(id: Int): Result<Subject>

    suspend fun getUserCollectionSubject(
        userName: String,
        subjectId: Int
    ): Result<UserSubjectCollection>

    suspend fun getRelatedPersons(subjectId: Int): Result<List<RelatedPerson>>

    suspend fun getSubjectCharacters(subjectId: Int): Result<List<RelatedCharacter>>

    suspend fun getCharacterDetail(characterId: Int): Result<CharacterDetail>

    suspend fun getCharacterPersons(characterId: Int): Result<List<CharacterPerson>>

    suspend fun getLoggedInUserInfo(accessToken: String): Result<User>

    suspend fun getPersonDetail(personId: Int): Result<PersonDetail>

    suspend fun modifyCollectionType(
        subjectId: Int,
        collectionType: CollectionType,
        accessToken: String
    ): Result<Unit>

    suspend fun modifyEpisodeCollectionType(
        subjectId: Int,
        episodeId: Int,
        type: EpisodeCollectionType,
        accessToken: String
    ): Result<Unit>
}