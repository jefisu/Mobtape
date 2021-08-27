package com.jefisu.mobtape.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityMainBinding
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.view.adapter.ServiceAdapter
import com.jefisu.mobtape.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: ServiceAdapter
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val list = listOf(
            ServiceModel(0, "Jose", type = "Reforma", category = "Almofada"),
            ServiceModel(1, "Jeferson", type = "Confeccao", category = "Canto Alemao"),
            ServiceModel(2, "Gustavo", type = "Reforma", category = "Cadeira"),
            ServiceModel(3, "Marcos", type = "Confeccao", category = "Sofa"),
        )
        listShow(list)
        observe()
    }

    //Apresentando a lista
    private fun listShow(list: List<ServiceModel>) {
        mAdapter = ServiceAdapter()
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