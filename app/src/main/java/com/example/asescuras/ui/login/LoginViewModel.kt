package com.example.asescuras.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.asescuras.data.LoginRepository
import com.example.asescuras.data.Result

import com.example.asescuras.R
// Classe LoginViewModel que gerencia os dados relacionados ao login
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    // MutableLiveData para armazenar o estado do formulário de login
    private val _loginForm = MutableLiveData<LoginFormState>()
    // LiveData exposta para observadores que desejam saber o estado do formulário
    val loginFormState: LiveData<LoginFormState> = _loginForm
    // MutableLiveData para armazenar o resultado do login
    private val _loginResult = MutableLiveData<LoginResult>()
    // LiveData exposta para observadores que desejam saber o resultado do login
    val loginResult: LiveData<LoginResult> = _loginResult
    // Função que tenta realizar o login com o nome de usuário e senha fornecidos
    fun login(username: String, password: String) {
        // O resultado do login é obtido do repositório de login
        val result = loginRepository.login(username, password)
        // Verifica se o resultado do login foi bem-sucedido
        if (result is Result.Success) {
            // Se o login for bem-sucedido, atualiza o loginResult com as informações do usuário
            _loginResult.value =
                // Se o login falhar, atualiza o loginResult com uma mensagem de erro
                LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }
    // Função que valida os dados de login e atualiza o estado do formulário
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            // Se não for válido, atualiza o estado do formulário com um erro de nome de usuário
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            // Se a senha não for válida, atualiza o estado do formulário com um erro de senha
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            // Se os dados forem válidos, atualiza o estado do formulário como válido
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // Função que verifica se o nome de usuário é válido
    private fun isUserNameValid(username: String): Boolean {
        // Se o nome de usuário contém '@', verifica se é um endereço de e-mail válido
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            // Caso contrário, verifica se o nome de usuário não está em branco
            username.isNotBlank()
        }
    }

    // Função que verifica se a senha é válida (neste caso, se tem mais de 5 caracteres)
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}