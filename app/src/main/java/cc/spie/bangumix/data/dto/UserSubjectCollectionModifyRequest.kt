package cc.spie.bangumix.data.dto

import cc.spie.bangumix.data.models.CollectionType
import kotlinx.serialization.Serializable

@Serializable
data class UserSubjectCollectionModifyRequest(
    val type: CollectionType? = null,
    val rate: Int? = null,
    val comment: String? = null,
)