package cc.spie.bangumix.data.repositories

import cc.spie.bangumix.utils.types.LoginInfo

interface AuthorizationRepository {
    suspend fun getAccessToken(code: String): Result<LoginInfo>

    suspend fun refreshAccessToken(refreshToken: String): Result<LoginInfo>
}