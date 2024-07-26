package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.dto.SearchRequest
import cc.spie.bangumix.data.models.SubjectType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class SearchFilterSerializer : KSerializer<SearchRequest.Filter> {

    override val descriptor: SerialDescriptor = SearchFilterSurrogate.serializer().descriptor

    private val dateFormat = LocalDate.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
    }

    override fun serialize(encoder: Encoder, value: SearchRequest.Filter) {
        val airDate = mutableListOf<String>()
        if (value.startAirDate != null) {
            airDate.add(">=${dateFormat.format(value.startAirDate)}")
        }
        if (value.endAirDate != null) {
            airDate.add("<=${dateFormat.format(value.endAirDate)}")
        }
        val rating = mutableListOf<String>()
        if (value.leastRating != null) {
            rating.add(">=${value.leastRating}")
        }
        if (value.mostRating != null) {
            rating.add("<=${value.mostRating}")
        }
        val rank = mutableListOf<String>()
        if (value.leastRank != null) {
            rank.add(">=${value.leastRank}")
        }
        if (value.mostRank != null) {
            rank.add("<=${value.mostRank}")
        }
        val surrogate = SearchFilterSurrogate(
            type = value.type,
            tag = value.tag,
            airDate = airDate,
            rating = rating,
            rank = rank,
            nsfw = value.nsfw,
        )
        encoder.encodeSerializableValue(SearchFilterSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): SearchRequest.Filter {
        val surrogate = decoder.decodeSerializableValue(SearchFilterSurrogate.serializer())
        val startAirDate = surrogate.airDate.find { it.startsWith(">=") }?.substring(2)
            ?.let { LocalDate.parse(it, dateFormat) }
        val endAirDate = surrogate.airDate.find { it.startsWith("<=") }?.substring(2)
            ?.let { LocalDate.parse(it, dateFormat) }
        val leastRating = surrogate.rating.find { it.startsWith(">=") }?.substring(2)?.toFloat()
        val mostRating = surrogate.rating.find { it.startsWith("<=") }?.substring(2)?.toFloat()
        val leastRank = surrogate.rank.find { it.startsWith(">=") }?.substring(2)?.toInt()
        val mostRank = surrogate.rank.find { it.startsWith("<=") }?.substring(2)?.toInt()
        return SearchRequest.Filter(
            type = surrogate.type,
            tag = surrogate.tag,
            startAirDate = startAirDate,
            endAirDate = endAirDate,
            leastRating = leastRating,
            mostRating = mostRating,
            leastRank = leastRank,
            mostRank = mostRank,
            nsfw = surrogate.nsfw,
        )
    }

    @Serializable
    private data class SearchFilterSurrogate(
        val type: List<SubjectType>,
        val tag: List<String>,
        @SerialName("air_date") val airDate: List<String>,
        @SerialName("rating") val rating: List<String>,
        @SerialName("rank") val rank: List<String>,
        val nsfw: Boolean,
    )
}