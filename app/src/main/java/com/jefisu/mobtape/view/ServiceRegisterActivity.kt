package com.jefisu.mobtape.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityServiceRegisterBinding
import com.jefisu.mobtape.service.dto.ServiceDto
import com.jefisu.mobtape.viewmodel.ServiceRegisterViewModel
import java.text.SimpleDateFormat
import java.util.*

class ServiceRegisterActivity : AppCompatActivity(),
    RadioGroup.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityServiceRegisterBinding
    private lateinit var selectedType: String
    private lateinit var selectedCategory: String
    private lateinit var mViewModel: ServiceRegisterViewModel

    // Configurando a formatação da data
    private val mBrazilLocale = Locale("pt", "br")
    private val mSimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", mBrazilLocale)
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Mobtape)
        binding = ActivityServiceRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.myToolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        //Instancia da ViewModel
        mViewModel = ViewModelProvider(this).get(ServiceRegisterViewModel::class.java)

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
                Snackbar.make(
                    binding.constraintServiceRegister,
                    "Erro ao inserir",
                    Snackbar.LENGTH_SHORT
                ).show()
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

    // Configurando a exibição do calendário
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Cria um calendário e atribui a data selecionada
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        // Converte a data selecionada para o formato imposto pelo SimpleDateFormat
        date = mSimpleDateFormat.format(calendar.time)
        binding.textDate.text = date
    }

    private fun listeners() {
        binding.imageRegisterSr.setOnClickListener {
            val service = ServiceDto(
                id = null,
                client = binding.textClientService.text.toString(),
                cpf = binding.textCpf.text.toString(),
                phone = binding.textPhone.text.toString(),
                type = selectedType,
                category = selectedCategory,
                date = date
            )
            mViewModel.save(service)
        }
        binding.textDate.setOnClickListener {
            // Obtém a instância do calendário
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Mostra o Datepicker utilizando os dados de hoje
            DatePickerDialog(this, this, year, month, day).show()
        }
        
        binding.radioGroup.setOnCheckedChangeListener(this)
    }

    /**
     * Atribuindo a formatação aos elementos
     */
    private fun formatMethods() {
        selectedDropDown()
        formatMask(binding.textCpf, "NNN.NNN.NNN-NN")
        formatMask(binding.textPhone, "+NN (NN) NNNNN-NNNN")
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
            selectedCategory = res[0]
        }

        // Salvando item escolhido no Banco de Dados
        binding.autoCompleteCategory.setOnItemClickListener { _, _, position, _ ->
            selectedCategory = res[position]
        }
    }
}