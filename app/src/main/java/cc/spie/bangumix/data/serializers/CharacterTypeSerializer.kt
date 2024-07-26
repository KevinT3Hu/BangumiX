package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.CharacterType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class CharacterTypeSerializer : KSerializer<CharacterType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CharacterType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): CharacterType {
        return CharacterType.fromValue(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: CharacterType) {
        encoder.encodeInt(value.ordinal + 1)
    }
}