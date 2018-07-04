package com.feelae.feelae.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.beust.klaxon.Klaxon
import com.daprlabs.aaron.swipedeck.SwipeDeck
import com.feelae.feelae.Constants
import com.feelae.feelae.R
import com.feelae.feelae.adapters.SwipeDeckAdapter
import com.feelae.feelae.http.APIController
import com.feelae.feelae.http.ServiceVolley
import com.feelae.feelae.models.Hints
import com.feelae.feelae.fragments.LoaderFragment
import kotlinx.android.synthetic.main.activity_waiting_room.*


class WaitingRoomActivity : AppCompatActivity() {
    private val loaderFragment: LoaderFragment by lazy {
        LoaderFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_room)
        waiting_room_cancel_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        showLoader()
        getHints()
        redirectToCallPageAfterDelay()
    }

    private fun getHints(){
        val cardStack = findViewById<View>(R.id.swipe_deck) as SwipeDeck
        val service = ServiceVolley()
        val apiController = APIController(service)
        apiController.get("hints", null) { response ->
            var hints = ArrayList<Hints>()
            if (response != null) {
                val result = Klaxon().parseArray<Hints>(response.toString())
                hints = ArrayList(result)
            }
            val adapter = SwipeDeckAdapter(hints, this)
            cardStack.setAdapter(adapter)
            hideLoader()
        }
    }

    private fun showLoader(){
        supportFragmentManager.beginTransaction().add(R.id.loader_container_layout, loaderFragment).commit()
    }

    private fun hideLoader(){
        supportFragmentManager.beginTransaction().remove(loaderFragment).commit()
    }

    private fun redirectToCallPageAfterDelay(){
        Handler().postDelayed({
            startActivity(Intent(this, CallActivity::class.java))
        }, Constants.SPLASH_TIME)
    }
}
