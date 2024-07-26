package cc.spie.bangumix.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char

val dateFormat = LocalDate.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
}