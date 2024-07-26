package cc.spie.bangumix.data.dto

import cc.spie.bangumix.data.models.EpisodeCollectionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyEpisodeCollectionTypeRequest(
    @SerialName("episode_id") val episodeId: List<Int>,
    val type: EpisodeCollectionType
)
