package cc.spie.bangumix.utils

import androidx.compose.runtime.mutableStateOf
import cc.spie.bangumix.data.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginStateHolder @Inject constructor() {

    val loggedIn = mutableStateOf(false)
    val loggedUser = mutableStateOf<User?>(null)
    val accessToken = mutableStateOf<String?>(null)

    fun recordLogin(user: User, accessToken: String) {
        loggedIn.value = true
        loggedUser.value = user
        this.accessToken.value = accessToken
    }

    fun logout() {
        loggedIn.value = false
        loggedUser.value = null
        accessToken.value = null
    }
}