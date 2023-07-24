package com.ebeid.passwordmanager.data.local

import android.content.SharedPreferences
import com.ebeid.passwordmanager.utils.Constants.PASSWORD_KEY
import javax.inject.Inject

class LocalPasswordService
@Inject
constructor(
    private val preferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {
    fun setPassword(password:String){
        editor.putString(PASSWORD_KEY, password)
        editor.apply()
    }

    fun getPassword():String?{
        return preferences.getString(PASSWORD_KEY, "")
    }
}