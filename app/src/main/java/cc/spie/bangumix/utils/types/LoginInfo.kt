package cc.spie.bangumix.utils.types

import cc.spie.bangumix.data.dto.AccessTokenResponse
import cc.spie.bangumix.data.serializers.ExpireDateSerializer
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginInfo(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") @Serializable(with = ExpireDateSerializer::class) val expireTime: LocalDateTime,
    @SerialName("refresh_token") val refreshCode: String
) {
    fun isExpired(): Boolean {
        return Clock.System.now() >= expireTime.toInstant(TimeZone.currentSystemDefault())
    }

    companion object {
        fun fromAccessTokenResponse(response: AccessTokenResponse): LoginInfo {
            return LoginInfo(
                accessToken = response.accessToken,
                expireTime = Clock.System.now()
                    .plus(response.expiresIn.toLong(), DateTimeUnit.SECOND)
                    .toLocalDateTime(TimeZone.currentSystemDefault()),
                refreshCode = response.refreshToken
            )
        }
    }
}
