package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.InfoBoxItem
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class InfoBoxItemStringValueSerializer : KSerializer<InfoBoxItem.InfoBoxValue.StringValue> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("StringValue", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): InfoBoxItem.InfoBoxValue.StringValue {
        return InfoBoxItem.InfoBoxValue.StringValue(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: InfoBoxItem.InfoBoxValue.StringValue) {
        encoder.encodeString(value.value)
    }
}