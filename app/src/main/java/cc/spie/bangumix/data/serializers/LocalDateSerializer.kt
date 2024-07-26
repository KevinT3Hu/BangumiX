package cc.spie.bangumix.data.serializers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class LocalDateSerializer : KSerializer<LocalDate?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    private val format = LocalDate.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
    }

    override fun deserialize(decoder: Decoder): LocalDate? {
        val value = decoder.decodeString()

        if (value.isBlank()) {
            return null
        }

        try {
            return LocalDate.parse(value, format)
        } catch (e: Throwable) {
            throw IllegalArgumentException("Invalid date format: $value")
        }
    }

    override fun serialize(encoder: Encoder, value: LocalDate?) {
        val date = value?.format(format)
        encoder.encodeString(date ?: "")
    }
}