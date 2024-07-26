package cc.spie.bangumix.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Images(
    val large: String,
    val common: String?,
    val medium: String,
    val small: String,
    val grid: String
)
