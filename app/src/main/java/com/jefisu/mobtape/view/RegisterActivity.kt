package com.jefisu.mobtape.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityRegisterBinding
import com.jefisu.mobtape.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Mobtape)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instancia da ViewModel
        mViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        //Eventos
        listeners()

        // Cria os observadores
        observe()
    }

    /**
     * Cria os observadores
     */
    private fun observe() {
        mViewModel.create.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.in_out_enter, R.anim.in_out_exit)
                finish()
            } else {
                val message = it.failure()
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun listeners() {
        binding.buttonRegister.setOnClickListener(this)
        binding.imageBack.setOnClickListener(this)
        binding.checkShowPassword.setOnCheckedChangeListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_register -> {
                val name = binding.textName.text.toString()
                val email = binding.textEmail.text.toString()
                val password = binding.textPasswordR.text.toString()
                val confirmPassword = binding.textConfirmPassword.text.toString()

                if (confirmPassword == password) {
                    mViewModel.insertUser(name, email, password)
                }
            }
            R.id.image_back -> {
                startActivity(Intent(this, LoginActivity::class.java))
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
                finish()
            }
        }
    }

    /** Evento de clique do CheckBox **/
    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        val checkBox = binding.checkShowPassword
        val password = binding.textPasswordR
        val passwordConfirm = binding.textConfirmPassword

        if (checkBox.isChecked) {
            //Para mostrar a senha
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            // Para esconder a senha
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        //Esta linha de código para colocar o ponteiro no final da senha
        password.setSelection(binding.textPasswordR.length())
        passwordConfirm.setSelection(binding.textConfirmPassword.length())
    }
}