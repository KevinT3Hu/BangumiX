package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    val id: Int,
    val type: EpisodeType,
    val name: String,
    @SerialName("name_cn") val nameCN: String,
    val sort: Int,
    val ep: Int?,
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("airdate") val airDate: LocalDate?,
    val comment: Int,
    val duration: String,
    val desc: String,
    val disc: Int,
    @SerialName("duration_seconds") val durationSeconds: Int?,
)
