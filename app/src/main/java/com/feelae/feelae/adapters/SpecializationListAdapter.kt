package com.feelae.feelae.adapters
import android.view.View
import com.feelae.feelae.models.Specialization
import com.hendraanggrian.pikasso.circle
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.specialization_item.view.*
import com.feelae.feelae.R

class SpecializationListAdapter (val specialization: Specialization) : AbstractItem<SpecializationListAdapter, SpecializationListAdapter.SpecializationViewHolder>() {
    override fun getType(): Int {
        return R.id.specialization_text
    }

    override fun getViewHolder(v: View?): SpecializationViewHolder {
        return SpecializationViewHolder(v)
    }

    override fun getLayoutRes(): Int {
        return R.layout.specialization_item
    }

    class SpecializationViewHolder(itemView: View?) : FastAdapter.ViewHolder<SpecializationListAdapter>(itemView) {
        override fun unbindView(item: SpecializationListAdapter?) {
            itemView.specialization_text.text = null
        }

        override fun bindView(item: SpecializationListAdapter?, payloads: MutableList<Any>?) {
            val specialization = item?.specialization
            itemView.specialization_text.text = specialization?.name
            Picasso.get().load(specialization?.imageUri).circle().into(itemView.specialization_image)
        }
    }
}