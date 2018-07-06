package com.feelae.feelae.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.feelae.feelae.R
import com.feelae.feelae.fragments.LoaderFragment
import com.feelae.feelae.helpers.PreferenceHelper
import com.feelae.feelae.models.SpecializationItem
import com.feelae.feelae.models.Specialization
import com.feelae.feelae.services.APIController
import com.feelae.feelae.services.ServiceVolley
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter.items
import kotlinx.android.synthetic.main.activity_specialization_list.*

class SpecializationListActivity : AppCompatActivity() {
    private val loaderFragment: LoaderFragment by lazy {
        LoaderFragment()
    }
    private lateinit var prefs: SharedPreferences
    private lateinit var mSelectedSpecialization: String
    private lateinit var mFastAdapter: FastAdapter<SpecializationItem>
    private lateinit var mItemAdapter: ItemAdapter<SpecializationItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialization_list)
        showLoader()
        prefs = PreferenceHelper.defaultPrefs(this)
        mItemAdapter = items()
        specialization_list_next_button.isEnabled = false
        mFastAdapter = FastAdapter.with(mItemAdapter)
        mFastAdapter.withSelectable(true)
        specialisation_recycler_view.layoutManager = LinearLayoutManager(this)
        specialisation_recycler_view.itemAnimator = DefaultItemAnimator()
        specialisation_recycler_view.adapter = mFastAdapter
        specialization_list_next_button.setOnClickListener {
            val intent = Intent(this, SymptomsActivity::class.java)
            intent.putExtra("specialization_EXTRA", mSelectedSpecialization)
            startActivity(intent)
        }

        specialization_list_cancel_button.setOnClickListener {
            this.finish()
        }

        mFastAdapter.withOnClickListener({ _, _, item, _ ->
            mSelectedSpecialization = item.specialization.slug
            specialization_list_next_button.isEnabled = true
            completionProgressBar.progress = 50
            true
        })
        getSpecializations()
    }

    private fun getSpecializations() {
        val service = ServiceVolley()
        val apiController = APIController(service)
        apiController.get("specializations", prefs.getString("token", null)) { response ->
            if (response != null) {
                val result = Klaxon().parseArray<Specialization>(response.toString())
                result?.forEach { it ->
                    when {
                        it.slug == "generaliste" -> it.image = R.drawable.ic_generaliste
                        it.slug == "gynecologie" -> it.image = R.drawable.ic_gynecologie
                        it.slug == "dermatologie" -> it.image = R.drawable.ic_dermatologie
                        it.slug == "nutrition" -> it.image = R.drawable.ic_nutrition
                        it.slug == "pediatrie" -> it.image = R.drawable.ic_pediatrie
                        it.slug == "psychologie" -> it.image = R.drawable.ic_pediatrie
                        it.slug == "dentaire" -> it.image = R.drawable.ic_dentaire
                        it.slug == "ophtamologie" -> it.image = R.drawable.ic_ophtalmologie
                    }
                }
                mItemAdapter.add(result?.map { SpecializationItem(it) })
                hideLoader()
            }
        }
    }

    private fun showLoader() {
        supportFragmentManager.beginTransaction().add(R.id.loader_container_layout, loaderFragment).commit()
    }

    private fun hideLoader() {
        supportFragmentManager.beginTransaction().remove(loaderFragment).commit()
    }
}
