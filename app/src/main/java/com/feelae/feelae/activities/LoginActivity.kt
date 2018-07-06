package com.feelae.feelae.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.feelae.feelae.*
import com.feelae.feelae.helpers.PreferenceHelper
import com.feelae.feelae.helpers.PreferenceHelper.set
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceHelper.defaultPrefs(this)
        setContentView(R.layout.activity_login)
        initListeners()
        initFormsValidations()
    }

    private fun initListeners() {
        loginButton.setOnClickListener(this)
        textViewLinkRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.loginButton -> login()
            R.id.textViewLinkRegister -> {
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    private fun initFormsValidations() {
        emailInput.validate({ s: String -> s.isNotEmpty() }, getString(R.string.error_message_email))
        passwordInput.validate({ s: String -> s.isNotEmpty() }, getString(R.string.error_message_password))
    }

    private fun login() {
        val service = ServiceVolley()
        val apiController = APIController(service)

        val params = JSONObject()
        params.put("email", emailInput.text.toString())
        params.put("password", passwordInput.text.toString())

        apiController.post("login", null, params) { response ->
            if (response != null) {
                prefs[Constants.TOKEN] = response.get("token")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}