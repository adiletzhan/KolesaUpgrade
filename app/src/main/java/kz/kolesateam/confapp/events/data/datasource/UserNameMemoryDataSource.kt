package kz.kolesateam.confapp.events.data.datasource

import kz.kolesateam.confapp.domain.UserNameDataSource

private const val DEFAULT_USER_NAME = "Guest"

class UserNameMemoryDataSource :
    UserNameDataSource {

    private lateinit var userName: String

    override fun getUserName(): String = userName ?: DEFAULT_USER_NAME

    override fun saveUserName(userName: String) {
        this.userName = userName
    }

}