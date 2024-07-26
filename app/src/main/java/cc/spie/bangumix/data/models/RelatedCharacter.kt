package cc.spie.bangumix.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RelatedCharacter(
    val id: Int,
    val name: String,
    val type: CharacterType,
    val images: Images,
    val relation: String,
    val actors: List<Person>?
)
