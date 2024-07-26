package cc.spie.bangumix.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchItem(
    val id: Int,
    val type: SubjectType,
    val date: String,
    val image: String,
    val summary: String,
    val name: String,
    @SerialName("name_cn") val nameCn: String,
    val tags: List<Tag>,
    val score: Float,
    val rank: Int
)
