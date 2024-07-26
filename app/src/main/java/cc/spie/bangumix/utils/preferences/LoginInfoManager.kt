package cc.spie.bangumix.utils.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cc.spie.bangumix.utils.annotations.preferences.LoginPreferences
import cc.spie.bangumix.utils.types.LoginInfo
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LoginInfoManager @Inject constructor(
    @LoginPreferences private val preferences: DataStore<Preferences>,
    private val serializer: Json,
) {

    private val mLoginInfoKey = "loginInfo"

    suspend fun getSavedLogin(): LoginInfo? {
        val pref = preferences.data.first()
        val data = pref[stringPreferencesKey(mLoginInfoKey)] ?: return null

        return serializer.decodeFromString(LoginInfo.serializer(), data)
    }

    suspend fun saveLogin(loginInfo: LoginInfo) {
        val data = serializer.encodeToString(LoginInfo.serializer(), loginInfo)

        preferences.edit { pref ->
            pref[stringPreferencesKey(mLoginInfoKey)] = data
        }
    }

}