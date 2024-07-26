package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.RatingCountSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val total: Int,
    @Serializable(with = RatingCountSerializer::class)
    val count: HashMap<Int, Int>,
    val score: Double,
    val rank: Int
)