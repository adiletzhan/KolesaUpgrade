package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R


class HelloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        val continueButton: Button = findViewById(R.id.continue_button)
        val mainActivityNameEditText: EditText = findViewById(R.id.activity_main_edit_text)

        mainActivityNameEditText.addTextChangedListener(object:TextWatcher {
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
            saveUserName(mainActivityNameEditText.text.toString())
            switchToHelloScreen()
        }
    }

    private fun saveUserName(userName: String){
        val sharedPreferences: SharedPreferences = getSharedPreferences(
                "SHARED_PREFERENCES",
                Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor
                .putString("User Name", userName)
                .apply()
    }

    private fun switchToHelloScreen(){
        val helloScreenIntent = Intent(this, HelloScreenActivity::class.java)
        startActivity(helloScreenIntent)
    }
}