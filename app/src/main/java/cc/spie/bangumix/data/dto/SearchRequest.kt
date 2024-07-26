package cc.spie.bangumix.data.dto

import cc.spie.bangumix.data.models.SubjectType
import cc.spie.bangumix.data.serializers.SearchFilterSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(
    val keyword: String,
    val sort: Sort,
    val filter: Filter,
) {
    @Serializable
    enum class Sort {
        @SerialName("match")
        MATCH,

        @SerialName("heat")
        HEAT,

        @SerialName("rank")
        RANK,

        @SerialName("score")
        SCORE;
    }

    @Serializable(with = SearchFilterSerializer::class)
    data class Filter(
        val type: List<SubjectType> = listOf(
            SubjectType.Anime,
            SubjectType.Book,
            SubjectType.Game,
            SubjectType.Music,
            SubjectType.Real
        ),
        val tag: List<String> = emptyList(),
        val startAirDate: LocalDate? = null,
        val endAirDate: LocalDate? = null,
        val leastRating: Float? = null,
        val mostRating: Float? = null,
        val leastRank: Int? = null,
        val mostRank: Int? = null,
        val nsfw: Boolean = false,
    )
}