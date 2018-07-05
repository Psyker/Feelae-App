package com.feelae.feelae.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import com.feelae.feelae.R
import kotlinx.android.synthetic.main.activity_symptoms.*


class SymptomsActivity : AppCompatActivity() {
    private var mSymptoms = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSymptoms()
        setContentView(R.layout.activity_symptoms)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mSymptoms)
        multiAutoCompleteTextView.setAdapter(adapter)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        symptoms_next_button.isEnabled = false
        progressBar2.progress = 50
        symptoms_next_button.setOnClickListener {
            val intent = Intent(this, WaitingRoomActivity::class.java)
            intent.putExtra("Symptoms", multiAutoCompleteTextView.text)
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
        mSymptoms = arrayListOf("Belgium", "France", "Italy", "Germany", "Spain")
    }


}
