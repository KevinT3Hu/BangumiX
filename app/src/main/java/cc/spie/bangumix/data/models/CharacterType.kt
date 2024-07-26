package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.CharacterTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = CharacterTypeSerializer::class)
enum class CharacterType {
    PERSON,
    SUIT,
    SHIP,
    ORGANIZATION;

    class UnknownCharacterTypeException(value: Int) :
        IllegalArgumentException("Unknown character type: $value")

    companion object {
        fun fromValue(value: Int): CharacterType {
            return when (value) {
                1 -> PERSON
                2 -> SUIT
                3 -> SHIP
                4 -> ORGANIZATION
                else -> throw UnknownCharacterTypeException(value)
            }
        }
    }
}