package cc.spie.bangumix.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val nickname: String,
    @SerialName("user_group") val userGroup: UserGroup,
    val avatar: Avatar,
    val sign: String,
)
