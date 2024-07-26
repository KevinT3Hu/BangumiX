package cc.spie.bangumix.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Avatar(
    val large: String,
    val medium: String,
    val small: String
)
