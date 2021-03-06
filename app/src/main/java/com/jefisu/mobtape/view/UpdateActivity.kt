package com.jefisu.mobtape.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityUpdateBinding
import com.jefisu.mobtape.service.constants.MobConstants.Companion.SERVICES
import com.jefisu.mobtape.service.dto.ServiceDto
import com.jefisu.mobtape.viewmodel.UpdateViewModel

class UpdateActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var mViewModel: UpdateViewModel
    private lateinit var selectedType: String
    private lateinit var selectedCategory: String
    private var mServiceId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Mobtape)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.myToolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        //Instanciando a ViewModel
        mViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        // Eventos
        listeners()
        formatMethods()

        // Buscando as informacoes, a partir do ID informado
        loadData()

        // Cria os observadores
        observe()
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
        binding.imageUpdate.setOnClickListener {
            val service = ServiceDto(
                id = mServiceId,
                client = binding.textClientUpdate.text.toString(),
                cpf = binding.textCpfUpdate.text.toString(),
                phone = binding.textPhoneUpdate.text.toString(),
                type = selectedType,
                category = selectedCategory,
            )
            mViewModel.update(service)
        }
        binding.radioGroup.setOnCheckedChangeListener(this)
    }

    /**
     * Cria os observadores
     */
    private fun observe() {
        mViewModel.service.observe(this, Observer {
            //Atribuindo os valores
            binding.textClientUpdate.setText(it.client)
            binding.textCpfUpdate.setText(it.cpf)
            binding.textPhoneUpdate.setText(it.phone)
            binding.autoCompleteCategory.setText(it.category)
            binding.textDate.text = it.date

            if (it.type == getString(R.string.REFORM)) {
                binding.radioReform.isChecked = true
            } else {
                binding.radioConfection.isChecked = true
            }
        })

        mViewModel.updateService.observe(this, Observer {
            if (!it) Snackbar.make(
                binding.constraintUpdate,
                "Erro ao atualizar",
                Snackbar.LENGTH_SHORT
            ).show()
            else finish()
        })
    }

    /** Carregando ??s informa????es do servi??o para atualiza????o **/
    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mServiceId = bundle.getInt(SERVICES.COLUMNS.ID)
            mViewModel.load(mServiceId)
        }
    }

    /**
     * Atribuindo a formata????o aos elementos
     */
    private fun formatMethods() {
        formatMask(binding.textPhoneUpdate, "+NN (NN) NNNNN-NNNN")
        formatMask(binding.textCpfUpdate, "NNN.NNN.NNN-NN")
    }

    /**
     * M??scara de formata????o para Celular/CPF
     **/
    private fun formatMask(textEdit: TextInputEditText, mask: String) {
        val simpleMaskFormatter = SimpleMaskFormatter(mask)
        val maskTextWatcher = MaskTextWatcher(textEdit, simpleMaskFormatter)
        textEdit.addTextChangedListener(maskTextWatcher)
    }
}