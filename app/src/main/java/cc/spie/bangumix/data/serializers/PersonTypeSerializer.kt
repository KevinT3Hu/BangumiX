package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.PersonType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class PersonTypeSerializer : KSerializer<PersonType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("PersonType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): PersonType {
        return when (decoder.decodeInt()) {
            1 -> PersonType.Individual
            2 -> PersonType.Company
            3 -> PersonType.Group
            else -> throw SerializationException("Unknown person type")
        }
    }

    override fun serialize(encoder: Encoder, value: PersonType) {
        encoder.encodeInt(value.ordinal + 1)
    }
}