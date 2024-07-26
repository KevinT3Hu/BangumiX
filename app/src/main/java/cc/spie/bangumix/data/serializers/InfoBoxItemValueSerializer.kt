package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.InfoBoxItem
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement

class InfoBoxItemValueSerializer :
    JsonContentPolymorphicSerializer<InfoBoxItem.InfoBoxValue>(InfoBoxItem.InfoBoxValue::class) {
    override fun selectDeserializer(element: JsonElement) = when (element) {
        is JsonArray -> InfoBoxItem.InfoBoxValue.ArrayValue.serializer()
        else -> InfoBoxItem.InfoBoxValue.StringValue.serializer()
    }
}