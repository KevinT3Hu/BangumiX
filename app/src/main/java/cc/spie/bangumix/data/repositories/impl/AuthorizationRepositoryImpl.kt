package cc.spie.bangumix.data.repositories.impl

import cc.spie.bangumix.data.apis.AuthorizationApi
import cc.spie.bangumix.data.dto.AccessTokenRequest
import cc.spie.bangumix.data.dto.RefreshTokenRequest
import cc.spie.bangumix.data.repositories.AuthorizationRepository
import cc.spie.bangumix.data.repositories.Repository
import cc.spie.bangumix.utils.types.LoginInfo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class AuthorizationRepositoryImpl @Inject constructor() : Repository(), AuthorizationRepository {
    @Inject
    lateinit var api: AuthorizationApi

    @Throws(Exception::class)
    override suspend fun getAccessToken(code: String) = invokeApi {
        api.getAccessTokenRequest(AccessTokenRequest(code = code)).let {
            LoginInfo.fromAccessTokenResponse(it)
        }
    }

    @Throws(Exception::class)
    override suspend fun refreshAccessToken(refreshToken: String) = invokeApi {
        api.refreshAccessTokenRequest(RefreshTokenRequest(refreshToken = refreshToken)).let {
            LoginInfo.fromAccessTokenResponse(it)
        }
    }
}