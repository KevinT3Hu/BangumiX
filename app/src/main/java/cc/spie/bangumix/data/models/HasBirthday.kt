package cc.spie.bangumix.data.models

interface HasBirthday {

    val birthYear: Int?
    val birthMonth: Int?
    val birthDay: Int?

    fun getBirthday(): String {
        return "${birthYear ?: "?"}-${birthMonth ?: "?"}-${birthDay ?: "?"}"
    }
}