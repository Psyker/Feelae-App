package com.feelae.feelae.models

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.content.ContextCompat
import android.view.View
import com.feelae.feelae.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.materialize.util.UIUtils
import kotlinx.android.synthetic.main.specialization_item.view.*

class SpecializationItem(val specialization: Specialization) : AbstractItem<SpecializationItem, SpecializationItem.SpecializationViewHolder>() {
    override fun getType(): Int {
        return R.id.specialization_text
    }

    override fun getViewHolder(v: View?): SpecializationViewHolder {
        return SpecializationViewHolder(v)
    }

    override fun getLayoutRes(): Int {
        return R.layout.specialization_item
    }

    class SpecializationViewHolder(itemView: View?) : FastAdapter.ViewHolder<SpecializationItem>(itemView) {
        override fun unbindView(item: SpecializationItem?) {
            itemView.specialization_text.text = null
        }

        override fun bindView(item: SpecializationItem?, payloads: MutableList<Any>?) {
            val ctx = itemView.context
            UIUtils.setBackground(itemView, FastAdapterUIUtils.getSelectableBackground(ctx, ContextCompat.getColor(ctx,R.color.mediumBlue), true))
            val specialization = item?.specialization
            itemView.specialization_text.text = specialization?.name
            if (specialization != null) {
                itemView.specialization_image.setImageResource(specialization.image)
            }
        }
    }
}