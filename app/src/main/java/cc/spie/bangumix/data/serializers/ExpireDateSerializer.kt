package cc.spie.bangumix.data.serializers

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ExpireDateSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ExpiresIn", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val value = decoder.decodeInt()
        val now = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()
        val expires = now.plus(value, DateTimeUnit.SECOND, timeZone)
        return expires.toLocalDateTime(timeZone)
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val now = Clock.System.now()
        val timeZone = TimeZone.currentSystemDefault()
        val expires = value.toInstant(timeZone).epochSeconds - now.epochSeconds
        encoder.encodeInt(expires.toInt())
    }
}