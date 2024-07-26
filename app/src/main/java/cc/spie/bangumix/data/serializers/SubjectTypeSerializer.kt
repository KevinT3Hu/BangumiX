package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.SubjectType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class SubjectTypeSerializer : KSerializer<SubjectType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SubjectType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): SubjectType {
        val value = decoder.decodeInt()
        try {
            val type = SubjectType.fromInt(value)
            return type
        } catch (e: SubjectType.UnknownSubjectTypeException) {
            throw SerializationException(e)
        }
    }

    override fun serialize(encoder: Encoder, value: SubjectType) {
        encoder.encodeInt(value.value)
    }
}