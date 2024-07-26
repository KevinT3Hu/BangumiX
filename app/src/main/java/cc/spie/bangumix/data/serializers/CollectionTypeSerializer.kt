package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.CollectionType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class CollectionTypeSerializer : KSerializer<CollectionType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CollectionType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): CollectionType {
        return CollectionType.fromValue(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: CollectionType) {
        encoder.encodeInt(value.value)
    }
}