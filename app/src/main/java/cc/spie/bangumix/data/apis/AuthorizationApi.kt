package cc.spie.bangumix.data.apis

import cc.spie.bangumix.data.dto.AccessTokenRequest
import cc.spie.bangumix.data.dto.AccessTokenResponse
import cc.spie.bangumix.data.dto.RefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthorizationApi {

    @POST("access_token")
    suspend fun getAccessTokenRequest(@Body request: AccessTokenRequest): AccessTokenResponse


    @POST("access_token")
    suspend fun refreshAccessTokenRequest(@Body request: RefreshTokenRequest): AccessTokenResponse

}





