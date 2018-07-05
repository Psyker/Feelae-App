package com.feelae.feelae.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.feelae.feelae.R
import com.feelae.feelae.models.SpecializationItem
import com.feelae.feelae.models.Specialization
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter.items
import kotlinx.android.synthetic.main.activity_specialization_list.*


class SpecializationListActivity : AppCompatActivity() {
    private var mSelectedSpecialization: Specialization? = null
    private lateinit var mFastAdapter: FastAdapter<SpecializationItem>
    private lateinit var mItemAdapter: ItemAdapter<SpecializationItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialization_list)
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
            mSelectedSpecialization = item.specialization
            specialization_list_next_button.isEnabled = true
            completionProgressBar.progress = 50
            true
        })
        getSpecializations()
    }

    private fun getSpecializations() {
        val list: ArrayList<Specialization> = ArrayList()
        list.add(Specialization("Médecine générale", R.drawable.ic_generaliste))
        list.add(Specialization("Gynécologie", R.drawable.ic_gynecologie))
        list.add(Specialization("Dermatologie", R.drawable.ic_dermatologie))
        list.add(Specialization("Nutrition", R.drawable.ic_nutrition))
        list.add(Specialization("Pédiatrie", R.drawable.ic_pediatrie))
        list.add(Specialization("Psychologie", R.drawable.ic_psychologie))
        list.add(Specialization("Dentaire", R.drawable.ic_dentaire))
        list.add(Specialization("Opthalmologie", R.drawable.ic_ophtalmologie))
        mItemAdapter.add(list.map { SpecializationItem(it) })
    }
}
