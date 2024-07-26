package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.EpisodeTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = EpisodeTypeSerializer::class)
enum class EpisodeType {
    MAIN,
    SP,
    OP,
    ED,
    TRAILER,
    MAD,
    OTHER;

    class UnknownEpisodeTypeException(value: Int) :
        IllegalArgumentException("Unknown episode type: $value")

    override fun toString(): String {
        return ordinal.toString()
    }

    companion object {
        fun fromInt(value: Int): EpisodeType {
            return when (value) {
                0 -> MAIN
                1 -> SP
                2 -> OP
                3 -> ED
                4 -> TRAILER
                5 -> MAD
                6 -> OTHER
                else -> throw UnknownEpisodeTypeException(value)
            }
        }
    }
}