package com.feelae.feelae.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.view.View
import com.feelae.feelae.Constants
import com.feelae.feelae.R
import android.widget.ImageView
import com.feelae.feelae.helpers.PreferenceHelper
import com.feelae.feelae.helpers.PreferenceHelper.set
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import com.feelae.feelae.utils.InputValidator
import org.json.JSONObject

class RegisterActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var prefs: SharedPreferences
    private lateinit var textInputLayoutFirstname: TextInputLayout
    private lateinit var textInputLayoutLastname: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var textInputEditTextFirstname: TextInputEditText
    private lateinit var textInputEditTextLastname: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText
    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatButtonLogin: ImageView
    private lateinit var inputValidator: InputValidator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()
    }


    private fun initViews() {
        textInputLayoutFirstname = findViewById<View>(R.id.textInputLayoutFirstname) as TextInputLayout
        textInputLayoutLastname = findViewById<View>(R.id.textInputLayoutLastname) as TextInputLayout
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword = findViewById<View>(R.id.textInputLayoutConfirmPassword) as TextInputLayout
        textInputEditTextFirstname = findViewById<View>(R.id.textInputEditTextFirstname) as TextInputEditText
        textInputEditTextLastname = findViewById<View>(R.id.textInputEditTextLastname) as TextInputEditText
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword = findViewById<View>(R.id.textInputEditTextConfirmPassword) as TextInputEditText
        appCompatButtonRegister = findViewById<View>(R.id.appCompatButtonRegister) as AppCompatButton
        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as ImageView

    }

    private fun initListeners() {
        appCompatButtonRegister.setOnClickListener(this)
        appCompatButtonLogin.setOnClickListener(this)

    }

    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        inputValidator = InputValidator(this)
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
        // TODO:  https://proandroiddev.com/easy-edittext-content-validation-with-kotlin-316d835d25b3
        if (!inputValidator.isInputFilled(textInputEditTextFirstname, textInputLayoutFirstname, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidator.isInputFilled(textInputEditTextLastname, textInputLayoutLastname, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidator.isInputFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidator.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidator.isInputFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidator.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword, textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }

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