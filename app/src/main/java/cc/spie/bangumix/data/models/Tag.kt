package cc.spie.bangumix.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String,
    val count: Int
)