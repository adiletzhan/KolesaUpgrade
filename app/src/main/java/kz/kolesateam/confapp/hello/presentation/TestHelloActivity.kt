package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R



class TestHelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)
        val helloText: TextView = findViewById(R.id.hello_screen_text_view)
        val userName: String = getUserName()
        helloText.text = "Hello, ${userName}!"
    }

    private fun getUserName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            SHARED_PREFERENCES,
            Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_NAME_KEY, "Someone") ?: "Someone"
    }
}
