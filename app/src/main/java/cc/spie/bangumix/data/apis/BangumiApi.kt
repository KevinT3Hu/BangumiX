package cc.spie.bangumix.data.apis

import cc.spie.bangumix.data.dto.ModifyEpisodeCollectionTypeRequest
import cc.spie.bangumix.data.dto.PagedData
import cc.spie.bangumix.data.dto.SearchRequest
import cc.spie.bangumix.data.dto.UserSubjectCollectionModifyRequest
import cc.spie.bangumix.data.models.Calendar
import cc.spie.bangumix.data.models.CharacterDetail
import cc.spie.bangumix.data.models.CharacterPerson
import cc.spie.bangumix.data.models.CollectionType
import cc.spie.bangumix.data.models.Episode
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BangumiApi {

    @GET("/calendar")
    suspend fun calendar(): List<Calendar>

    @POST("/v0/search/subjects")
    suspend fun search(
        @Body request: SearchRequest,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PagedData<SearchItem>

    @GET("/v0/subjects/{id}")
    suspend fun getSubject(@Path("id") id: Int): Subject

    @GET("/v0/subjects/{id}/persons")
    suspend fun getRelatedPersons(@Path("id") subjectId: Int): List<RelatedPerson>

    @GET("/v0/me")
    suspend fun getLoggedInUserInfo(@Header("Authorization") accessToken: String): User

    @GET("/v0/users/{username}/collections")
    suspend fun getUserCollections(
        @Path("username") userName: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Header("Authorization") accessToken: String? = null,
        @Query("subject_type") subjectType: SubjectType? = null,
        @Query("type") type: CollectionType? = null,
    ): PagedData<UserSubjectCollection>

    @GET("/v0/users/{username}/collections/{subject_id}")
    suspend fun getUserCollectionSubject(
        @Path("username") userName: String,
        @Path("subject_id") subjectId: Int,
    ): UserSubjectCollection

    @POST("/v0/users/-/collections/{subject_id}")
    @Headers("Content-Type: application/json")
    suspend fun modifyUserCollection(
        @Path("subject_id") subjectId: Int,
        @Body request: UserSubjectCollectionModifyRequest,
        @Header("Authorization") accessToken: String
    )

    @GET("/v0/users/-/collections/{subject_id}/episodes")
    suspend fun getEpisodeCollections(
        @Path("subject_id") subjectId: Int,
        @Header("Authorization") accessToken: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("episode_type") episodeType: EpisodeType? = null
    ): PagedData<UserEpisodeCollection>

    @GET("/v0/episodes")
    suspend fun getEpisodes(
        @Query("subject_id") subjectId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("type") episodeType: EpisodeType? = null
    ): PagedData<Episode>

    @PATCH("/v0/users/-/collections/{subject_id}/episodes")
    @Headers("Content-Type: application/json")
    suspend fun modifyEpisodeCollectionType(
        @Path("subject_id") subjectId: Int,
        @Body request: ModifyEpisodeCollectionTypeRequest,
        @Header("Authorization") accessToken: String
    )

    @GET("/v0/subjects/{subject_id}/characters")
    suspend fun getSubjectCharacters(@Path("subject_id") subjectId: Int): List<RelatedCharacter>

    @GET("/v0/characters/{character_id}")
    suspend fun getCharacterDetail(@Path("character_id") characterId: Int): CharacterDetail

    @GET("/v0/characters/{character_id}/persons")
    suspend fun getCharacterPersons(@Path("character_id") characterId: Int): List<CharacterPerson>

    @GET("/v0/persons/{person_id}")
    suspend fun getPersonDetail(@Path("person_id") personId: Int): PersonDetail
}