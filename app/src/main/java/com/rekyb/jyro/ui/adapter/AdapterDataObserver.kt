package com.rekyb.jyro.ui.adapter

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView

class AdapterDataObserver(
    private val recyclerView: RecyclerView,
    private val parcelable: Parcelable,
) : RecyclerView.AdapterDataObserver() {

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        recyclerView.layoutManager?.onRestoreInstanceState(parcelable)
    }
}
