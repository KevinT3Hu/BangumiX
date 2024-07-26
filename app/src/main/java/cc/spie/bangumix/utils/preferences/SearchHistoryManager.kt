package cc.spie.bangumix.utils.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cc.spie.bangumix.utils.annotations.preferences.SearchHistoryPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchHistoryManager @Inject constructor(
    @SearchHistoryPreferences private val preferences: DataStore<Preferences>
) {
    private val mSearchHistoryKey = "searchHistory"
    private val mSearchHistoryFlow = MutableStateFlow(emptyList<String>())
    val searchHistoryFlow get() = mSearchHistoryFlow

    init {
        CoroutineScope(Dispatchers.IO).launch {
            searchHistoryFlow.value = getSearchHistory()
        }
    }

    private suspend fun getSearchHistory(): List<String> {
        val pref = preferences.data.first()
        return pref[stringPreferencesKey(mSearchHistoryKey)]?.split(",") ?: emptyList()
    }

    suspend fun addSearchHistory(keyword: String) {
        val history = getSearchHistory().toMutableList()
        if (history.contains(keyword)) {
            history.remove(keyword)
        }
        history.add(0, keyword)
        preferences.edit { pref ->
            pref[stringPreferencesKey(mSearchHistoryKey)] = history.joinToString(",")
        }
        searchHistoryFlow.value = history
    }

    suspend fun removeSearchHistory(keyword: String) {
        val history = getSearchHistory().toMutableList()
        history.remove(keyword)
        preferences.edit { pref ->
            pref[stringPreferencesKey(mSearchHistoryKey)] = history.joinToString(",")
        }
        searchHistoryFlow.value = history
    }
}