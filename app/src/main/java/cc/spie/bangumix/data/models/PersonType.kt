package cc.spie.bangumix.data.models

import androidx.annotation.StringRes
import cc.spie.bangumix.R
import cc.spie.bangumix.data.serializers.PersonTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = PersonTypeSerializer::class)
enum class PersonType(@StringRes val label: Int) {
    Individual(R.string.individual),
    Company(R.string.company),
    Group(R.string.group),
}