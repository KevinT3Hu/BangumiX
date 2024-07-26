package cc.spie.bangumix.data.models

import cc.spie.bangumix.ui.extensions.toGender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetail(
    val id: Int,
    val name: String,
    val type: CharacterType,
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

    val bloodType
        get() = when (mBloodType) {
            1 -> "A"
            2 -> "B"
            3 -> "AB"
            4 -> "O"
            else -> null
        }
}