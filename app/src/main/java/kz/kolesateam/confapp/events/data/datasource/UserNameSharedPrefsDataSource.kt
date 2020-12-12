package kz.kolesateam.confapp.events.data.datasource

import android.content.SharedPreferences
import kz.kolesateam.confapp.domain.UserNameDataSource

const val USER_NAME_KEY = "user_name"
private const val EMPTY_STRING = ""
private const val DEFAULT_USER_NAME = "Guest"

class UserNameSharedPrefsDataSource(
        private val sharedPreferences: SharedPreferences
): UserNameDataSource {
    override fun getUserName(): String = sharedPreferences.getString(USER_NAME_KEY, EMPTY_STRING)
            ?: DEFAULT_USER_NAME

    override fun saveUserName(
            userName: String
    ){
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
    }
}