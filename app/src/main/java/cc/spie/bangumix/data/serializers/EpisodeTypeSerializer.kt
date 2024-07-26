package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.EpisodeType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class EpisodeTypeSerializer : KSerializer<EpisodeType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("EpisodeType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): EpisodeType {
        return EpisodeType.fromInt(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: EpisodeType) {
        encoder.encodeInt(value.ordinal)
    }
}