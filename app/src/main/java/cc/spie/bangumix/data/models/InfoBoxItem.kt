package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.InfoBoxItemArrayValueSerializer
import cc.spie.bangumix.data.serializers.InfoBoxItemStringValueSerializer
import cc.spie.bangumix.data.serializers.InfoBoxItemValueSerializer
import cc.spie.bangumix.data.serializers.KVOrVSerializer
import kotlinx.serialization.Serializable

@Serializable
data class InfoBoxItem(
    val key: String,
    val value: InfoBoxValue
) {
    @Serializable(with = InfoBoxItemValueSerializer::class)
    sealed interface InfoBoxValue {
        @Serializable(with = InfoBoxItemStringValueSerializer::class)
        data class StringValue(val value: String) : InfoBoxValue

        @Serializable(with = InfoBoxItemArrayValueSerializer::class)
        data class ArrayValue(val value: List<KVOrV>) : InfoBoxValue
    }

    @Serializable(with = KVOrVSerializer::class)
    sealed interface KVOrV {
        @Serializable
        data class KV(val k: String, val v: String) : KVOrV

        @Serializable
        data class V(val v: String) : KVOrV
    }
}
