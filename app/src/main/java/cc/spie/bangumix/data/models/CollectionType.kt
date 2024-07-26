package cc.spie.bangumix.data.models

import androidx.annotation.StringRes
import cc.spie.bangumix.R
import cc.spie.bangumix.data.serializers.CollectionTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = CollectionTypeSerializer::class)
enum class CollectionType(val value: Int, @StringRes val label: Int) {
    WISH(1, R.string.collection_type_wish),
    WATCHED(2, R.string.collection_type_watched),
    WATCHING(3, R.string.collection_type_watching),
    DELAYED(4, R.string.collection_type_delayed),
    DROPPED(5, R.string.collection_type_dropped);

    override fun toString(): String {
        return value.toString()
    }

    class UnknownCollectionTypeException(value: Int) : Exception("Unknown collection type: $value")

    companion object {
        fun fromValue(value: Int): CollectionType {
            return when (value) {
                1 -> WISH
                2 -> WATCHED
                3 -> WATCHING
                4 -> DELAYED
                5 -> DROPPED
                else -> throw UnknownCollectionTypeException(value)
            }
        }
    }
}