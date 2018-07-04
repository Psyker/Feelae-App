package com.feelae.feelae.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.feelae.feelae.R
import com.feelae.feelae.adapters.SpecializationListAdapter
import com.feelae.feelae.models.Specialization
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import kotlinx.android.synthetic.main.activity_specialization_list.*

class SpecializationListActivity : AppCompatActivity() {
    private var mSpecializations: ArrayList<Specialization>? = null
    private var mSpecialization: Specialization? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialization_list)
        //specialisation_recycler_view.layoutManager = LinearLayoutManager(this)
        val itemAdapter = FastItemAdapter<SpecializationListAdapter>()
        itemAdapter.add(mSpecializations?.map { SpecializationListAdapter(it) })
        //specialisation_recycler_view.adapter = itemAdapter
        itemAdapter.withOnClickListener({ _, _, item, _ ->
            mSpecialization = item.specialization
            true
        })

        next_button.setOnClickListener{
            //val intent = Intent(this, CandidateDetail::class.java)
            intent.putExtra("specialization_EXTRA", mSpecialization)
            startActivity(intent)
        }
    }
}
