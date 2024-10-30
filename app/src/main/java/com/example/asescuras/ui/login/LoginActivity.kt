package com.example.asescuras.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.asescuras.R
import com.example.asescuras.databinding.ActivityLoginBinding


/**
 * Activity responsável por lidar com a autenticação do usuário.
 */
class LoginActivity : AppCompatActivity() {

    /**
     * ViewModel que gerencia a lógica de negócios da autenticação.
     */
    private lateinit var loginViewModel: LoginViewModel

    /**
     * Binding que vincula a UI ao código.
     */
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Infla o layout da activity e o vincula ao binding.
         */
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Obtém referências para os componentes da UI.
         */
        val username = binding.teLogin
        val password = binding.tePassword
        val login = binding.btEntrada
        val loading = binding.progbar

        /**
         * Cria uma instância do ViewModel e o vincula à activity.
         */
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        /**
         * Observa o estado do formulário de login e atualiza a UI de acordo.
         */
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        /**
         * Observa o resultado da autenticação e atualiza a UI de acordo.
         */
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        /**
         * Atualiza o ViewModel com os dados do formulário de login quando o usuário digita algo.
         */
        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
        // Inicia a RegisterActivity
        val tvRegister: TextView = binding.tv_register // Certifique-se de que o ID esteja correto
        tvRegister.setOnClickListener {
            val intent = Intent(this, ::class.java)
            startActivity(intent)
        }
    }

    /**
     * Atualiza a UI com as informações do usuário após uma autenticação bem-sucedida.
     */
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * Mostra uma mensagem de erro para o usuário em caso de falha na autenticação.
     */
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extensão de função para simplificar a configuração de uma ação afterTextChanged para componentes EditText.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

}