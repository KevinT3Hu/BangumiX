package cc.spie.bangumix.data.models

import cc.spie.bangumix.ui.extensions.toGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetail(
    val id: Int,
    val name: String,
    val type: PersonType,
    val career: List<PersonCareer>,
    val images: Images?,
    val summary: String,
    val locked: Boolean,
    @SerialName("infobox") val infoBox: List<InfoBoxItem>?,
    @SerialName("gender") private val mGender: String?,
    @SerialName("blood_type") private val mBloodType: Int?,
    @SerialName("birth_year") override val birthYear: Int?,
    @SerialName("birth_mon") override val birthMonth: Int?,
    @SerialName("birth_day") override val birthDay: Int?,
) : HasBirthday {
    val gender get() = mGender?.toGender()
}