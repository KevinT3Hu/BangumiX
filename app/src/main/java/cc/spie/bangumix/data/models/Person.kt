package cc.spie.bangumix.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Int,
    val name: String,
    val type: PersonType,
    val career: List<PersonCareer>,
    val images: Images,
    @SerialName("short_summary") val shortSummary: String,
    val locked: Boolean
)
