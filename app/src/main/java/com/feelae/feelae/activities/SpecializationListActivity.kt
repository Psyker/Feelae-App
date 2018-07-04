package com.feelae.feelae.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.feelae.feelae.R
import com.feelae.feelae.adapters.SpecializationListAdapter
import com.feelae.feelae.fragments.LoaderFragment
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import com.feelae.feelae.models.Specialization
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import kotlinx.android.synthetic.main.activity_specialization_list.*

class SpecializationListActivity : AppCompatActivity() {
    private var mSpecializations: ArrayList<Specialization> = ArrayList()
    private var mSpecialization: Specialization? = null

    private val loaderFragment: LoaderFragment by lazy {
        LoaderFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialization_list)
        next_button.isEnabled = false
        showLoader()
        getSpecializations()

        next_button.setOnClickListener{
            val intent = Intent(this, SymptomsActivity::class.java)
            intent.putExtra("specialization_EXTRA", mSpecialization)
            startActivity(intent)
        }
    }

    private fun getSpecializations(){
        val service = ServiceVolley()
        val apiController = APIController(service)
        apiController.get("specializations", null) { response ->
            if (response != null) {
                val result = Klaxon().parseArray<Specialization>(response.toString())
                mSpecializations = ArrayList(result)
            }
            specialisation_recycler_view.layoutManager = LinearLayoutManager(this)
            val itemAdapter = FastItemAdapter<SpecializationListAdapter>()
            itemAdapter.add(mSpecializations.map { SpecializationListAdapter(it) })
            specialisation_recycler_view.adapter = itemAdapter
            itemAdapter.withOnClickListener({ _, _, item, _ ->
                mSpecialization = item.specialization
                next_button.isEnabled = true
                true
            })
            hideLoader()
        }
    }

    private fun showLoader(){
        supportFragmentManager.beginTransaction().add(R.id.loader_container_layout, loaderFragment).commit()
    }

    private fun hideLoader(){
        supportFragmentManager.beginTransaction().remove(loaderFragment).commit()
    }
}
