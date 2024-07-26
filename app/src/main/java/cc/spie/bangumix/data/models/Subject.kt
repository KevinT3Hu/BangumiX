package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val id: Int,
    val type: SubjectType,
    val name: String,
    @SerialName("name_cn") val nameCN: String,
    val summary: String,
    val nsfw: Boolean,
    val locked: Boolean,
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate?,
    val platform: String,
    val images: Images,
    @SerialName("infobox") val infoBox: List<InfoBoxItem>?,
    val volumes: Int,
    val eps: Int,
    @SerialName("total_episodes") val totalEpisodes: Int,
    val rating: Rating,
    val collection: Collection,
    val tags: List<Tag>
)