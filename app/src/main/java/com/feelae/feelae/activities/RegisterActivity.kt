package com.feelae.feelae.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.widget.ImageView
import com.feelae.feelae.*
import com.feelae.feelae.helpers.PreferenceHelper
import com.feelae.feelae.helpers.PreferenceHelper.set
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import com.feelae.feelae.utils.InputValidator
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        prefs = PreferenceHelper.defaultPrefs(this)
        initFormsValidations()
        initListeners()
    }

    private fun initFormsValidations() {
        // https://proandroiddev.com/easy-edittext-content-validation-with-kotlin-316d835d25b3
        textInputEditTextFirstname.validate({ s: String -> s.isNotEmpty() }, getString(R.string.error_message_name))
        textInputEditTextLastname.validate({ s: String -> s.isNotEmpty() }, getString(R.string.error_message_name))
        textInputEditTextEmail.validate({ s: String -> s.isValidEmail() }, getString(R.string.error_message_email))
        textInputEditTextConfirmPassword.validate({ s: String -> s.isEqualTo(textInputEditTextPassword.text.toString()) }, getString(R.string.error_message_password))
    }

    private fun initListeners() {
        appCompatButtonRegister.setOnClickListener(this)
        appCompatButtonLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.appCompatButtonRegister -> register()
                R.id.appCompatButtonLogin -> finish()
            }
        }
    }

    private fun register() {
        val service = ServiceVolley()
        val apiController = APIController(service)

        val params = JSONObject()
        params.put("firstname", textInputEditTextFirstname.text.toString())
        params.put("lastname", textInputEditTextLastname.text.toString())
        params.put("email", textInputEditTextEmail.text.toString())
        params.put("password", textInputEditTextPassword.text.toString())

        apiController.post("register", null, params) { response ->
            if (response != null) {
                prefs[Constants.TOKEN] = response.get("token")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                emptyInputEditText()
            } else {
                emptyInputEditText()
            }
        }
    }

    private fun emptyInputEditText() {
        textInputEditTextFirstname.text = null
        textInputEditTextLastname.text = null
        textInputEditTextEmail.text = null
        textInputEditTextPassword.text = null
        textInputEditTextConfirmPassword.text = null
    }
}



