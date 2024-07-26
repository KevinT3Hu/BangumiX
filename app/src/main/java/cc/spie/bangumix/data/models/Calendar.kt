package cc.spie.bangumix.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Calendar(
    val weekday: Weekday,
    val items: List<CalendarSubject>
) {
    @Serializable
    data class Weekday(
        val en: String,
        val cn: String,
        val ja: String,
        val id: Int
    )

    @Serializable
    data class CalendarSubject(
        val id: Int,
        val url: String,
        val type: SubjectType,
        val name: String,
        @SerialName("name_cn") val nameCn: String,
        val summary: String,
        @SerialName("air_date") val airDate: String,
        @SerialName("air_weekday") val airWeekday: Int,
        val images: Images,
        val eps: Int,
        @SerialName("eps_count") val epsCount: Int,
        val rating: Rating,
        val rank: Int,
        val collection: Collection
    )


}
