package com.chidi.pokemongo.data.local

import android.content.Context
import androidx.preference.PreferenceManager
import com.chidi.pokemongo.presentation.utils.Constants
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(context: Context) {
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun setUserToken(token: String) {
        pref.edit().putString(Constants.TOKEN, token).apply()
    }

    fun getUserToken(): String? {
        return pref.getString(Constants.TOKEN, null)
    }

    fun clearAllStoredData() {
        pref.edit().clear().apply()
    }
}