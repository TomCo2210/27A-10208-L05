package dev.tomco.a27a_10208_l05.utilities

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.DataStore.FILE_NAME
)

class DataStoreManager private constructor(context: Context) {
    private val  dataStore = context.applicationContext.dataStore

    companion object{
        @Volatile
        private var instance : DataStoreManager? = null

        fun init(context: Context): DataStoreManager{
            return instance ?: synchronized(this){
                instance ?: DataStoreManager(context).also { instance = it }
            }
        }

        fun getInstance(): DataStoreManager{
            return instance ?: throw IllegalStateException(
                "DataStoreManager must be initialized by calling init(context) before use."
            )
        }
    }

    private val safeData: Flow<Preferences> = dataStore.data.catch {
        exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
    }

    suspend fun putString(key: String, value: String) {
        dataStore.edit {
            preferences -> preferences[stringPreferencesKey(key)] = value
        }
    }

    fun getString(key: String, defaultValue: String): Flow<String> =
        safeData.map{
        preferences -> preferences[stringPreferencesKey(key)] ?: defaultValue
        }

}