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

const val SHARED_PREFERENCES = "application"
const val USER_NAME_KEY = "user_name"

class HelloActivity : AppCompatActivity() {

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
            navigateToHelloScreenActivity()
        }
    }

    private fun saveUserName(userName: String){
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                SHARED_PREFERENCES,
                Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor
                .putString(USER_NAME_KEY, userName)
                .apply()
    }

    private fun navigateToHelloScreenActivity(){
        val helloScreenIntent = Intent(this, TestHelloActivity::class.java)
        startActivity(helloScreenIntent)
    }
}