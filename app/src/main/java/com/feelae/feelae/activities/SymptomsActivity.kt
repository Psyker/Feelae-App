package com.feelae.feelae.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import com.beust.klaxon.Klaxon
import com.feelae.feelae.R
import com.feelae.feelae.models.Symptoms
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import kotlinx.android.synthetic.main.activity_symptoms.*


class SymptomsActivity : AppCompatActivity() {
    private var mSymptomNames = emptyList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSymptoms()
        setContentView(R.layout.activity_symptoms)
        symptoms_next_button.isEnabled = false
        progressBar2.progress = 50
        symptoms_next_button.setOnClickListener {
            val intent = Intent(this, WaitingRoomActivity::class.java)
            intent.putExtra("Symptoms", multiAutoCompleteTextView.text)
            intent.putExtra("specialization", this.intent.extras.getString("specialization_EXTRA"))
            startActivity(intent)
        }

        symptoms_cancel_button.setOnClickListener {
            this.finish()
        }

        multiAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                symptoms_next_button.isEnabled = true
                progressBar2.progress = 100
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun getSymptoms() {
        val service = ServiceVolley()
        val apiController = APIController(service)
        apiController.get("symptoms", null) { response ->
            if (response != null) {
                val result = Klaxon().parseArray<Symptoms>(response.toString())
                mSymptomNames =  ArrayList(result).map{ it.name }
                val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mSymptomNames)
                multiAutoCompleteTextView.setAdapter(adapter)
                multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
            }
        }
    }


}
