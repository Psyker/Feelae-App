package com.feelae.feelae

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.andtinder.model.CardModel
import com.andtinder.view.SimpleCardStackAdapter
import com.beust.klaxon.Klaxon
import com.feelae.feelae.http.APIController
import com.feelae.feelae.http.ServiceVolley
import com.feelae.feelae.model.Hints
import kotlinx.android.synthetic.main.activity_waiting_room.*


class WaitingRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)
        val r = resources
        val adapter = SimpleCardStackAdapter(this)
        val cardModel = CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1))
        cardModel.setOnClickListener {
            Log.i("Swipeable Cards","I am pressing the card");
        }

        adapter.add(cardModel)

        layoutview.adapter = adapter
    }

    private fun getHints(){
        val service = ServiceVolley()
        val apiController = APIController(service)
        apiController.get("hints", null) { response ->
            if (response != null) {
                val result = Klaxon().parseArray<Hints>(response.toString())
                val hints = ArrayList(result)
            }
        }
    }
}
