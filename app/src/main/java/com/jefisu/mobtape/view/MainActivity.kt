package com.jefisu.mobtape.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityMainBinding
import com.jefisu.mobtape.service.constants.MobConstants.Companion.SERVICES.COLUMNS
import com.jefisu.mobtape.service.listener.ServiceListener
import com.jefisu.mobtape.view.adapter.ServiceAdapter
import com.jefisu.mobtape.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: ServiceAdapter
    private lateinit var mListener: ServiceListener
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instanciando a ViewModel
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //Eventos
        listShow()
        listeners()

        // Cria os observadores
        observe()
    }

    //Atualiza a lista antes de ser mostrada novamente
    override fun onResume() {
        super.onResume()
        listShow()
    }

    private fun listeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, ServiceRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //Carregando a lista
    private fun listShow() {
        //Instancia
        mListener = object : ServiceListener {
            override fun onClick(id: Int) {
                val intent = Intent(applicationContext, UpdateActivity::class.java)
                val bundle = Bundle().apply {
                    putInt(COLUMNS.ID, id)
                }
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                listShow()
            }
        }

        //Apresentando a lista ao usuario
        val list = mViewModel.getUpdateList()
        mAdapter = ServiceAdapter(mListener)
        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(applicationContext)
            this.adapter = mAdapter
            mAdapter.updateList(list)
        }
    }

    //Observando a ViewModel
    private fun observe() {
        mViewModel.logout.observe(this, Observer {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }

    //Exibindo o menu options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    //Definindo os cliques dos items do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> mViewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

}