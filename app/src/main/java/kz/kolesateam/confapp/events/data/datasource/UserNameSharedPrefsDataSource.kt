package kz.kolesateam.confapp.events.data.datasource

import android.content.SharedPreferences
import kz.kolesateam.confapp.hello.presentation.USER_NAME_KEY


private const val EMPTY_STRING = ""

class UserNameSharedPrefsDataSource(
        private val sharedPreferences: SharedPreferences
): UserNameDataSource {
    override fun getUserName(): String? = sharedPreferences.getString(USER_NAME_KEY, EMPTY_STRING)

    override fun saveUserName(
            userName: String
    ){
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
    }
}