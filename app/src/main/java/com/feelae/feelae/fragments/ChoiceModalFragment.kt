package com.feelae.feelae.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.feelae.feelae.activities.SpecializationListActivity
import com.feelae.feelae.activities.MainActivity
import com.feelae.feelae.R
import kotlinx.android.synthetic.main.doctor_choice.*

class ChoiceModalFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.doctor_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_modal_text.setOnClickListener {
            val mainActivity = this.activity as MainActivity
            mainActivity.closeChoiceModal()
        }

        my_doctors_button.isEnabled = false

        available_doctor_button.setOnClickListener{
            startActivity(Intent(activity, SpecializationListActivity::class.java))
        }
    }


}