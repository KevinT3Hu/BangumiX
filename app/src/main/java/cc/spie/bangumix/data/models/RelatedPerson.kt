package cc.spie.bangumix.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RelatedPerson(
    val id: Int,
    val name: String,
    val type: PersonType,
    val career: PersonCareer,
    val images: Images?,
    val relation: String
)
