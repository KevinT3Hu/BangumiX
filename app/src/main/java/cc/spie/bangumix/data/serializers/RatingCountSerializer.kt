package cc.spie.bangumix.data.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer

class RatingCountSerializer : KSerializer<HashMap<Int, Int>> {
    override val descriptor: SerialDescriptor = serializer<HashMap<String, Int>>().descriptor

    override fun deserialize(decoder: Decoder): HashMap<Int, Int> {
        val map = HashMap<Int, Int>()
        val stringMap = decoder.decodeSerializableValue(serializer<HashMap<String, Int>>())
        stringMap.forEach { (key, value) ->
            map[key.toInt()] = value
        }
        return map
    }

    override fun serialize(encoder: Encoder, value: HashMap<Int, Int>) {
        val stringMap = value.mapKeys { it.key.toString() }
        encoder.encodeSerializableValue(
            serializer<HashMap<String, Int>>(),
            stringMap as HashMap<String, Int>
        )
    }
}