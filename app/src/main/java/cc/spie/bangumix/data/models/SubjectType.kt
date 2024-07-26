package cc.spie.bangumix.data.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import cc.spie.bangumix.R
import cc.spie.bangumix.data.serializers.SubjectTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = SubjectTypeSerializer::class)
enum class SubjectType(val value: Int, @StringRes val label: Int) {
    Book(1, R.string.subject_type_book),
    Anime(2, R.string.subject_type_anime),
    Music(3, R.string.subject_type_music),
    Game(4, R.string.subject_type_game),
    Real(6, R.string.subject_type_three);

    fun getColor(): Color {
        return when (this) {
            Book -> Color(0xFFFFD700)
            Anime -> Color(0xFF32CD32)
            Music -> Color(0xFF00BFFF)
            Game -> Color(0xFFFF6347)
            Real -> Color(0xFFFF8C00)
        }
    }

    @Composable
    fun getIcon(): ImageVector {
        return when (this) {
            Anime -> Icons.Default.PlayArrow
            Music -> ImageVector.vectorResource(id = R.drawable.music)
            Book -> ImageVector.vectorResource(id = R.drawable.book)
            Game -> ImageVector.vectorResource(id = R.drawable.game)
            Real -> ImageVector.vectorResource(id = R.drawable.tv)
        }
    }

    class UnknownSubjectTypeException(value: Int) : Exception("Unknown subject type: $value")

    companion object {
        @Throws(UnknownSubjectTypeException::class)
        fun fromInt(value: Int): SubjectType {
            return when (value) {
                1 -> Book
                2 -> Anime
                3 -> Music
                4 -> Game
                6 -> Real
                else -> throw UnknownSubjectTypeException(value)
            }
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}