package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.InfoBoxItem
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class InfoBoxItemArrayValueSerializer : KSerializer<InfoBoxItem.InfoBoxValue.ArrayValue> {
    override val descriptor: SerialDescriptor = ListSerializer(
        InfoBoxItem.KVOrV.serializer()
    ).descriptor

    override fun deserialize(decoder: Decoder): InfoBoxItem.InfoBoxValue.ArrayValue {
        val list = decoder.decodeSerializableValue(ListSerializer(InfoBoxItem.KVOrV.serializer()))
        return InfoBoxItem.InfoBoxValue.ArrayValue(list)
    }

    override fun serialize(encoder: Encoder, value: InfoBoxItem.InfoBoxValue.ArrayValue) {
        encoder.encodeSerializableValue(ListSerializer(InfoBoxItem.KVOrV.serializer()), value.value)
    }
}