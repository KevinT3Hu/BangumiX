package cc.spie.bangumix.data.models

import cc.spie.bangumix.data.serializers.UserGroupSerializer
import kotlinx.serialization.Serializable

@Serializable(with = UserGroupSerializer::class)
enum class UserGroup(val value: Int) {
    ADMIN(1),
    BANGUMI_ADMIN(2),
    DOUJIN_ADMIN(3),
    BLOCKED(4),
    BANNED(5),
    CHARS_ADMIN(8),
    WIKI_ADMIN(9),
    USER(10),
    WIKIER(11);

    class UnknownUserGroupException(value: Int) : Exception("Unknown user group: $value")

    companion object {
        @Throws(UnknownUserGroupException::class)
        fun fromValue(value: Int): UserGroup {
            return when (value) {
                1 -> ADMIN
                2 -> BANGUMI_ADMIN
                3 -> DOUJIN_ADMIN
                4 -> BLOCKED
                5 -> BANNED
                8 -> CHARS_ADMIN
                9 -> WIKI_ADMIN
                10 -> USER
                11 -> WIKIER
                else -> throw UnknownUserGroupException(value)
            }
        }
    }
}