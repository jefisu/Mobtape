package com.jefisu.mobtape.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityServiceRegisterBinding
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.viewmodel.ServiceRegisterViewModel

class ServiceRegisterActivity : AppCompatActivity(),
    RadioGroup.OnCheckedChangeListener {

    private lateinit var binding: ActivityServiceRegisterBinding
    private lateinit var selectedType: String
    private lateinit var selectedCategory: String
    private lateinit var mViewModel: ServiceRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Mobtape)
        binding = ActivityServiceRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia da ViewModel
        mViewModel = ViewModelProvider(this).get(ServiceRegisterViewModel::class.java)

        /** Escoder a supportActionBar **/
        supportActionBar!!.hide()

        // Eventos
        listeners()
        formatMethods()

        // Cria os observadores
        observe()

        // Default
        binding.radioConfection.isChecked = true
    }

    /**
     * Cria os observadores
     */
    private fun observe() {
        mViewModel.saveService.observe(this, Observer {
            if (it) {
                finish()
            } else {
                Toast.makeText(this, "Erro ao inserir", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Definindo o clique do RadioButton
     */
    override fun onCheckedChanged(r: RadioGroup, p1: Int) {
        if (binding.radioConfection.isChecked) {
            binding.radioConfection.isChecked = true
            selectedType = getString(R.string.CONFECTION)
        } else {
            binding.radioReform.isChecked = true
            selectedType = getString(R.string.REFORM)
        }
    }

    private fun listeners() {
        binding.imageBackSr.setOnClickListener { finish() }
        binding.buttonRegisterSr.setOnClickListener {
            val service = ServiceModel(
                id = null,
                client = binding.textClientService.text.toString(),
                cpf = binding.textCpf.text.toString(),
                phone = binding.textPhone.text.toString(),
                type = selectedType,
                category = selectedCategory
            )
            mViewModel.save(service)
        }
        binding.radioGroup.setOnCheckedChangeListener(this)
    }

    /**
     * Atribuindo a formatação aos elementos
     */
    private fun formatMethods() {
        selectedDropDown()
        formatMask(binding.textCpf, "NNN.NNN.NNN-NN")
        formatMask(binding.textPhone, "(NN) NNNNN-NNNN")
    }

    /**
     * Máscara de formatação para Celular/CPF
    **/
    private fun formatMask(textEdit: TextInputEditText, mask: String) {
        val simpleMaskFormatter = SimpleMaskFormatter(mask)
        val maskTextWatcher = MaskTextWatcher(textEdit, simpleMaskFormatter)
        textEdit.addTextChangedListener(maskTextWatcher)
    }

    /**
     * Definindo o Adapter, e configurando para exibir a lista de Arrays na tela
     * **/
    private fun selectedDropDown() {
        val res = resources.getStringArray(R.array.CATEGORY)
        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_item, res)
        binding.autoCompleteCategory.setAdapter(arrayAdapter)

        selectedCategory = ""
        if (selectedCategory.isEmpty()) {
            selectedCategory = "Almofada"
        }

        // Salvando item escolhido no Banco de Dados
        binding.autoCompleteCategory.setOnItemClickListener { _, _, position, _ ->
            selectedCategory = res[position]
        }
    }
}