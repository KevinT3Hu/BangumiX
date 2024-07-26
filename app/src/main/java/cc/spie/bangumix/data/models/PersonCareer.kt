package cc.spie.bangumix.data.models

import androidx.annotation.StringRes
import cc.spie.bangumix.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PersonCareer(@StringRes val label: Int) {
    @SerialName("producer")
    PRODUCER(R.string.producer),

    @SerialName("mangaka")
    MANGAKA(R.string.mangaka),

    @SerialName("artist")
    ARTIST(R.string.artist),

    @SerialName("seiyu")
    SEIYU(R.string.seiyu),

    @SerialName("writer")
    WRITER(R.string.writer),

    @SerialName("illustrator")
    ILLUSTRATOR(R.string.illustrator),

    @SerialName("actor")
    ACTOR(R.string.actor)
}