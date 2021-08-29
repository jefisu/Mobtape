package com.jefisu.mobtape.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jefisu.mobtape.R
import com.jefisu.mobtape.service.listener.ServiceListener
import com.jefisu.mobtape.service.model.ServiceModel

class ServiceViewHolder(view: View, val listener: ServiceListener) : RecyclerView.ViewHolder(view) {

    // Obtém os elementos de interface
    private val textClient = itemView.findViewById<TextView>(R.id.text_client)
    private val textKindOfService = itemView.findViewById<TextView>(R.id.text_type_service)
    private val textCategory = itemView.findViewById<TextView>(R.id.text_category)

    fun bindData(service: ServiceModel) {

        // Atribui valores
        textCategory.text = service.category
        textClient.text = service.client
        textKindOfService.text = service.type

        // Atribui eventos
        val id = service.id
        itemView.setOnClickListener {
            listener.onClick(id!!)
        }
        itemView.setOnLongClickListener {
            AlertDialog.Builder(it.context)
                .setTitle(it.context.getString(R.string.DELETE_RECORD))
                .setMessage(it.context.getString(R.string.DO_YOU_WANT_TO_DELETE_THE_SERVICE))
                .setPositiveButton(it.context.getString(R.string.REMOVE)) { dialog, _ ->
                    listener.onDelete(id!!)
                }
                .setNeutralButton(it.context.getString(R.string.CANCEL), null)
                .show()
            true
        }
    }
}