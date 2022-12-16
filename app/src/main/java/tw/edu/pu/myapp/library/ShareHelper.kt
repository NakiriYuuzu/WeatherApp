package tw.edu.pu.myapp.library

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder


class ShareHelper(val context: Context) {
    val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun <T> put(`object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        pref.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = pref.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    inline fun <reified T> isValueExist(key: String): Boolean {
        return get<T>(key) != null
    }

    fun remove(key: String) {
        pref.edit().remove(key).apply()
    }

    fun clear() {
        pref.edit().clear().apply()
    }
}