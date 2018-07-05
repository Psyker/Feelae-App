package com.feelae.feelae.activities

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import com.feelae.feelae.Constants
import com.feelae.feelae.R
import com.feelae.feelae.helpers.PreferenceHelper
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import com.feelae.feelae.utils.InputValidator
import org.json.JSONObject
import com.feelae.feelae.helpers.PreferenceHelper.set

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var prefs: SharedPreferences
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    private lateinit var inputValidator: InputValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceHelper.defaultPrefs(this)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }

    private fun initViews() {
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as AppCompatButton
        textViewLinkRegister = findViewById<View>(R.id.textViewLinkRegister) as AppCompatTextView
    }

    private fun initListeners() {
        appCompatButtonLogin.setOnClickListener(this)
        textViewLinkRegister.setOnClickListener(this)
    }

    private fun initObjects() {
        inputValidator = InputValidator(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonLogin -> login()
            R.id.textViewLinkRegister -> {
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    private fun login() {
        if (!inputValidator.isInputFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidator.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidator.isInputFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return
        }

        val service = ServiceVolley()
        val apiController = APIController(service)

        val params = JSONObject()
        params.put("email", textInputEditTextEmail.text.toString())
        params.put("password", textInputEditTextPassword.text.toString())

        apiController.post("login", null, params) { response ->
            if (response != null) {
                prefs[Constants.TOKEN] = response.get("token")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun emptyInputEditText() {
        textInputEditTextEmail.text = null
        textInputEditTextPassword.text = null
    }

}