package com.jefisu.mobtape.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jefisu.mobtape.R
import com.jefisu.mobtape.databinding.ActivityLoginBinding
import com.jefisu.mobtape.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Inicializa eventos
        setListeners();
        observe()

        // Verifica se usuário está logado
//        verifyLoggedUser()
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
        mViewModel.login.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val message = it.failure()
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Verifica se usuário está logado
     */
    private fun verifyLoggedUser() {
//        mViewModel.verifyLoggedUser()
    }

    /**
     * Autentica usuário
     */
    private fun handleLogin() {
        val email = binding.textLogin.text.toString()
        val password = binding.textPassword.text.toString()

        mViewModel.doLogin(email, password)
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
       binding.textNotRegister.setOnClickListener(this)
        binding.imageAccess.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        if (v.id == R.id.image_access) {
            handleLogin()
        } else if (v.id == R.id.text_not_register) {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}