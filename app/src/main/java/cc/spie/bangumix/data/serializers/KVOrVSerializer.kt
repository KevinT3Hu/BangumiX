package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.InfoBoxItem
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class KVOrVSerializer :
    JsonContentPolymorphicSerializer<InfoBoxItem.KVOrV>(InfoBoxItem.KVOrV::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<InfoBoxItem.KVOrV> {
        val o = element.jsonObject
        return if (o.containsKey("k")) {
            InfoBoxItem.KVOrV.KV.serializer()
        } else {
            InfoBoxItem.KVOrV.V.serializer()
        }
    }
}