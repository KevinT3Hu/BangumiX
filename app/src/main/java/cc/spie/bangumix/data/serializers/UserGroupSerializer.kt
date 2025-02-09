package cc.spie.bangumix.data.serializers

import cc.spie.bangumix.data.models.UserGroup
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class UserGroupSerializer : KSerializer<UserGroup> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UserGroup", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): UserGroup {
        return UserGroup.fromValue(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: UserGroup) {
        encoder.encodeInt(value.value)
    }
}