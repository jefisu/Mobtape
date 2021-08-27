package com.jefisu.mobtape.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jefisu.mobtape.R
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.view.viewholder.ServiceViewHolder

class ServiceAdapter : RecyclerView.Adapter<ServiceViewHolder>() {

    private var mList = listOf<ServiceModel>()

    fun updateList(list: List<ServiceModel>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = mList[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}