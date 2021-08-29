package com.jefisu.mobtape.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jefisu.mobtape.R
import com.jefisu.mobtape.service.listener.ServiceListener
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.view.viewholder.ServiceViewHolder

class ServiceAdapter(
    private val mListener: ServiceListener
) : RecyclerView.Adapter<ServiceViewHolder>() {

    // Lista de serviços
    private var mList = listOf<ServiceModel>()

    /**
     * Atualização da lista de serviços
     */
    fun updateList(list: List<ServiceModel>) {
        mList = list
        notifyDataSetChanged()
    }

    /**
     * Faz a criação do layout da linha
     * Faz a criação de várias linhas que vão mostrar cada um dos serviços
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return ServiceViewHolder(view, mListener)
    }

    /**
     * Para cada linha, este método é chamado
     * É responsável por atribuir os valores de cada item para uma linha específica
     */
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = mList[position]
        holder.bindData(item)
    }

    /**
     * Qual o tamanho da RecyclerView
     */
    override fun getItemCount(): Int {
        return mList.size
    }
}