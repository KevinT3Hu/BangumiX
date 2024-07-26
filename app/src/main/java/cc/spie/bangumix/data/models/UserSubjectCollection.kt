package cc.spie.bangumix.data.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class UserSubjectCollection(
    @SerialName("subject_id") val subjectId: Int,
    @SerialName("subject_type") val subjectType: SubjectType,
    val rate: Int,
    val type: CollectionType,
    val comment: String?,
    val tags: List<String>,
    @SerialName("ep_status") val epStatus: Int,
    @SerialName("vol_status") val volStatus: Int,
    @SerialName("private") val isPrivate: Boolean,
)
