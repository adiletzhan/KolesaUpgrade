package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.di.SHARED_PREFS_DATA_SOURCE
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

const val SHARED_PREFERENCES = "application"
const val USER_NAME_KEY = "user_name"

class HelloActivity : AppCompatActivity() {

    private val userNameDataSource: UserNameDataSource by inject(named(SHARED_PREFS_DATA_SOURCE))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val continueButton: Button = findViewById(R.id.activity_hello_continue_button)
        val nameEditText: EditText = findViewById(R.id.activity_hello_name_edit_text)

        nameEditText.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val isInputEmpty: Boolean = p0.toString().isBlank()
                continueButton.isEnabled = !isInputEmpty
            }
        })
        continueButton.setOnClickListener {
            saveUserName(nameEditText.text.toString())
            navigateToUpcomingEventsActivity()
        }
    }

    private fun saveUserName(userName: String){
        userNameDataSource.saveUserName(userName)
    }

    private fun navigateToUpcomingEventsActivity(){
        val upcomingEventsIntent = Intent(this, UpcomingEventsActivity::class.java)
        startActivity(upcomingEventsIntent)
    }
}