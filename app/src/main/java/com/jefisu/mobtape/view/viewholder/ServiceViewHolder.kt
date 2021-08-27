package com.jefisu.mobtape.view.viewholder

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jefisu.mobtape.R
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.view.RegisterActivity

class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textClient = itemView.findViewById<TextView>(R.id.text_client)
    private val textKindOfService = itemView.findViewById<TextView>(R.id.text_type_service)
    private val textCategory = itemView.findViewById<TextView>(R.id.text_category)

    fun bindData(service: ServiceModel) {

        textCategory.text = service.category
        textClient.text = service.client
        textKindOfService.text = service.type


        itemView.setOnClickListener {
            val intent = Intent(it.context, RegisterActivity::class.java)
            it.context.startActivity(intent)
        }

    }
}