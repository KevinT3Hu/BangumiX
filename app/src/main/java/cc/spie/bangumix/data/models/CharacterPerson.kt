package cc.spie.bangumix.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterPerson(
    val id: Int,
    val name: String,
    val type: CharacterType,
    val images: Images?,
    @SerialName("subject_id") val subjectId: Int,
    @SerialName("subject_name") val subjectName: String,
    @SerialName("subject_name_cn") val subjectNameCN: String,
    val staff: String?
)
