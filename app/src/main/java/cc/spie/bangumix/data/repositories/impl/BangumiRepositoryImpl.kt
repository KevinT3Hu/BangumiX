package cc.spie.bangumix.data.repositories.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cc.spie.bangumix.data.apis.BangumiApi
import cc.spie.bangumix.data.dto.ModifyEpisodeCollectionTypeRequest
import cc.spie.bangumix.data.dto.SearchRequest
import cc.spie.bangumix.data.dto.UserSubjectCollectionModifyRequest
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.Episode
import cc.spie.bangumix.data.models.EpisodeCollectionType
import cc.spie.bangumix.data.models.EpisodeType
import cc.spie.bangumix.data.models.SearchItem
import cc.spie.bangumix.data.models.Subject
import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.models.UserEpisodeCollection
import cc.spie.bangumix.data.models.UserSubjectCollection
import cc.spie.bangumix.data.paging.SubjectSearchPagingSource
import cc.spie.bangumix.data.paging.UserCollectionPagingSource
import cc.spie.bangumix.data.repositories.BangumiRepository
import cc.spie.bangumix.data.repositories.Repository
import cc.spie.bangumix.utils.LoginStateHolder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BangumiRepositoryImpl @Inject constructor() : Repository(), BangumiRepository {

    private val mSubjectCache = mutableMapOf<Int, Subject>()
    private val mSubjectCollectionCache = mutableMapOf<Int, UserSubjectCollection>()

    @Inject
    lateinit var api: BangumiApi

    @Inject
    lateinit var loginStateHolder: LoginStateHolder

    override suspend fun calendar() = invokeApi {
        api.calendar()
    }

    override fun initSearch(
        keyword: String,
        sort: SearchRequest.Sort,
        filter: SearchRequest.Filter
    ): Flow<PagingData<SearchItem>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { SubjectSearchPagingSource(api, keyword, sort, filter) }
    ).flow

    override fun getUserCollections(
        subjectType: SubjectType?,
        collectionType: CollectionType?
    ): Flow<PagingData<UserSubjectCollection>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            UserCollectionPagingSource(
                api,
                loginStateHolder.loggedUser.value?.username!!,
                loginStateHolder.accessToken.value?.let { "Bearer $it" },
                subjectType,
                collectionType
            )
        }
    ).flow

    override suspend fun getEpisodeCollections(
        subjectId: Int,
        episodeType: EpisodeType?
    ): Result<List<UserEpisodeCollection>> {
        val token = loginStateHolder.accessToken.value!!
        val limit = 30
        val episodes = mutableListOf<UserEpisodeCollection>()
        var total: Int
        var result = invokeApi {
            api.getEpisodeCollections(
                subjectId,
                "Bearer $token",
                limit,
                0,
                episodeType
            )
        }
        do {
            if (result.isFailure) {
                return Result.failure(result.exceptionOrNull()!!)
            } else {
                val res = result.getOrNull()!!
                episodes.addAll(res.data)
                total = res.total
                if (episodes.size < total) {
                    result = invokeApi {
                        api.getEpisodeCollections(
                            subjectId,
                            "Bearer $token",
                            limit,
                            episodes.size,
                            episodeType
                        )
                    }
                } else {
                    return Result.success(episodes)
                }
            }
        } while (true)
    }

    override suspend fun getEpisodes(
        subjectId: Int,
        episodeType: EpisodeType?
    ): Result<List<Episode>> {
        val limit = 30
        val episodes = mutableListOf<Episode>()
        var total: Int
        var result = invokeApi { api.getEpisodes(subjectId, limit, 0, episodeType) }
        do {
            if (result.isFailure) {
                return Result.failure(result.exceptionOrNull()!!)
            } else {
                val res = result.getOrNull()!!
                episodes.addAll(res.data)
                total = res.total
                if (episodes.size < total) {
                    result =
                        invokeApi { api.getEpisodes(subjectId, limit, episodes.size, episodeType) }
                } else {
                    return Result.success(episodes)
                }
            }
        } while (true)
    }

    override suspend fun getSubject(id: Int) = invokeApi {
        mSubjectCache[id] ?: api.getSubject(id).also { mSubjectCache[id] = it }
    }

    override suspend fun getUserCollectionSubject(userName: String, subjectId: Int) = invokeApi {
        mSubjectCollectionCache[subjectId] ?: api.getUserCollectionSubject(userName, subjectId)
            .also { mSubjectCollectionCache[subjectId] = it }
    }

    override suspend fun getRelatedPersons(subjectId: Int) = invokeApi {
        api.getRelatedPersons(subjectId)
    }

    override suspend fun getSubjectCharacters(subjectId: Int) = invokeApi {
        api.getSubjectCharacters(subjectId)
    }

    override suspend fun getCharacterDetail(characterId: Int) = invokeApi {
        api.getCharacterDetail(characterId)
    }

    override suspend fun getCharacterPersons(characterId: Int) = invokeApi {
        api.getCharacterPersons(characterId)
    }

    override suspend fun getLoggedInUserInfo(accessToken: String) = invokeApi {
        api.getLoggedInUserInfo("Bearer $accessToken")
    }

    override suspend fun getPersonDetail(personId: Int) = invokeApi {
        api.getPersonDetail(personId)
    }

    override suspend fun modifyCollectionType(
        subjectId: Int,
        collectionType: CollectionType,
        accessToken: String
    ) = invokeApi {
        mSubjectCollectionCache.remove(subjectId)
        api.modifyUserCollection(
            subjectId,
            UserSubjectCollectionModifyRequest(type = collectionType),
            "Bearer $accessToken"
        )
    }

    override suspend fun modifyEpisodeCollectionType(
        subjectId: Int,
        episodeId: Int,
        type: EpisodeCollectionType,
        accessToken: String
    ) = invokeApi {
        api.modifyEpisodeCollectionType(
            subjectId, ModifyEpisodeCollectionTypeRequest(
                episodeId = listOf(episodeId),
                type = type
            ), "Bearer $accessToken"
        )
    }
}