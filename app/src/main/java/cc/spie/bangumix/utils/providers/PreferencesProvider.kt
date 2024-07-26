package cc.spie.bangumix.utils.providers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import cc.spie.bangumix.utils.annotations.preferences.LoginPreferences
import cc.spie.bangumix.utils.annotations.preferences.OptionsPreferences
import cc.spie.bangumix.utils.annotations.preferences.SearchHistoryPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val LOGIN_PREFERENCES = "loginPreferences"
private const val SEARCH_HISTORY_PREFERENCES = "searchHistory"
private const val OPTIONS_PREFERENCES = "options"

@InstallIn(SingletonComponent::class)
@Module
object PreferencesProvider {

    @Singleton
    @LoginPreferences
    @Provides
    fun provideLoginPreferencesDataStore(@ApplicationContext context: Context) =
        createPreferences(context, LOGIN_PREFERENCES)

    @Singleton
    @SearchHistoryPreferences
    @Provides
    fun provideSearchHistoryPreferencesDataStore(@ApplicationContext context: Context) =
        createPreferences(context, SEARCH_HISTORY_PREFERENCES)

    @Singleton
    @OptionsPreferences
    @Provides
    fun provideOptionsPreferencesDataStore(@ApplicationContext context: Context) =
        createPreferences(context, OPTIONS_PREFERENCES)

    private fun createPreferences(context: Context, name: String): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context, name)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(name) }
        )
    }
}