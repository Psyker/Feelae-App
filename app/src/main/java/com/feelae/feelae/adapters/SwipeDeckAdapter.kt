package com.feelae.feelae.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.feelae.feelae.R
import com.feelae.feelae.models.Hints
import com.squareup.picasso.Picasso


class SwipeDeckAdapter(private val data: ArrayList<Hints>, private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(context)
        val v = layoutInflater.inflate(R.layout.swipe_deck, parent,false)
        val imageView = v.findViewById(R.id.card_image) as ImageView
        val textViewContent = v.findViewById(R.id.card_content_text) as TextView
        val textViewTitle = v.findViewById(R.id.card_title_text) as TextView
        val item = getItem(position) as Hints
        Picasso.get().load(item.imageUri).fit().centerCrop().into(imageView)
        textViewContent.text = item.body
        textViewTitle.text = item.title
        return v
    }
}