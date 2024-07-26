package cc.spie.bangumix.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val wish: Int,
    val collect: Int,
    val doing: Int,
    @SerialName("on_hold") val onHold: Int,
    val dropped: Int
)