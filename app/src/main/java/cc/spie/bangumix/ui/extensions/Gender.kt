package cc.spie.bangumix.ui.extensions

import androidx.annotation.StringRes
import cc.spie.bangumix.R

@StringRes
fun String.toGender(): Int {
    return when (this) {
        "male" -> R.string.male
        "female" -> R.string.female
        else -> R.string.unknown
    }
}