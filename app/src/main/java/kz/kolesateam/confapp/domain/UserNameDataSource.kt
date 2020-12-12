package kz.kolesateam.confapp.domain

interface UserNameDataSource {
    fun getUserName(): String

    fun saveUserName(
            userName: String
    )
}