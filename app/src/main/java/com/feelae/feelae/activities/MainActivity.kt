package com.feelae.feelae.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.feelae.feelae.R
import com.feelae.feelae.fragments.ChoiceModalFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val choiceModalFragment: ChoiceModalFragment by lazy {
        ChoiceModalFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        call_psy_button.setOnClickListener {
            showChoiceModal()
        }
    }

    fun closeChoiceModal() {
        supportFragmentManager.beginTransaction().remove(choiceModalFragment).commit()
    }

    private fun showChoiceModal() {
        supportFragmentManager.beginTransaction().add(R.id.dialog_container_layout, choiceModalFragment).commit()
    }
}
