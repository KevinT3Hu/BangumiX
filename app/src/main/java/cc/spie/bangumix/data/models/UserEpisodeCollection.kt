package cc.spie.bangumix.data.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class UserEpisodeCollection(
    val episode: Episode,
    val type: EpisodeCollectionType
)
