package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.EpisodeCollectionType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class EpisodeCollectionTypeSerializer : KSerializer<EpisodeCollectionType> {
    override val descriptor = PrimitiveSerialDescriptor("EpisodeCollectionType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): EpisodeCollectionType {
        return EpisodeCollectionType.fromValue(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: EpisodeCollectionType) {
        encoder.encodeInt(value.ordinal)
    }
}