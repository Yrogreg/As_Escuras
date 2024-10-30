package com.example.asescuras.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asescuras.data.LoginDataSource
import com.example.asescuras.data.LoginRepository

/**
 * Fábrica de ViewModel para instanciar o LoginViewModel.
 * Necessária porque o LoginViewModel possui um construtor não vazio.
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST") // Ignora o aviso de cast não seguro
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verifica se a classe do ViewModel solicitado é do tipo LoginViewModel
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            // Se for, cria uma nova instância do LoginViewModel
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource() // Cria uma instância do LoginDataSource
                )
            ) as T // Faz o cast para o tipo T
        }
        // Lança uma exceção se a classe do ViewModel não for reconhecida
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}