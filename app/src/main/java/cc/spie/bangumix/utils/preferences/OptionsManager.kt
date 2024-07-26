package cc.spie.bangumix.utils.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import cc.spie.bangumix.utils.annotations.preferences.OptionsPreferences
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject

class OptionsManager @Inject constructor(
    @OptionsPreferences private val preferences: DataStore<Preferences>,
    private val serializer: Json
) {
    private val mLastSelectedIndexKey = intPreferencesKey("lastSelectedIndex")

    suspend fun getLastSelectedIndex(): Int {
        val pref = preferences.data.first()
        return pref[mLastSelectedIndexKey] ?: 0
    }
}