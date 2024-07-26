package cc.spie.bangumix.data.models

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cc.spie.bangumix.R
import cc.spie.bangumix.data.serializers.EpisodeCollectionTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = EpisodeCollectionTypeSerializer::class)
enum class EpisodeCollectionType(@StringRes val label: Int) {
    NONE(R.string.collection_type_none),
    WISH(R.string.collection_type_wish),
    WATCHED(R.string.collection_type_watched),
    DROPPED(R.string.collection_type_dropped);

    @Composable
    fun getColor(): Color {
        return when (this) {
            NONE, WISH -> MaterialTheme.colorScheme.primaryContainer
            WATCHED -> Color.Cyan
            DROPPED -> MaterialTheme.colorScheme.surfaceContainerLow
        }
    }

    class UnknownEpisodeCollectionTypeException(value: Int) :
        IllegalArgumentException("Unknown episode collection type: $value")

    companion object {
        fun fromValue(value: Int): EpisodeCollectionType {
            return when (value) {
                0 -> NONE
                1 -> WISH
                2 -> WATCHED
                3 -> DROPPED
                else -> throw UnknownEpisodeCollectionTypeException(value)
            }
        }
    }
}