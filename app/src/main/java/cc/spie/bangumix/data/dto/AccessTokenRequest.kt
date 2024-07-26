package cc.spie.bangumix.data.dto

import cc.spie.bangumix.BuildConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    @SerialName("grant_type") val grantType: String = "authorization_code",
    @SerialName("client_id") val clientId: String = BuildConfig.CLIENT_ID,
    @SerialName("client_secret") val clientSecret: String = BuildConfig.APP_SECRET,
    val code: String,
    @SerialName("redirect_uri") val redirectUri: String = BuildConfig.REDIRECT_URI
)