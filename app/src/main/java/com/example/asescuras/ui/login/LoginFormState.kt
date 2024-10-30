package com.example.asescuras.ui.login

/**
 * Classe que representa o estado de validação dos dados do formulário de login.
 *
 * Esta classe é usada para encapsular os erros de validação e o estado geral do formulário,
 * permitindo que a interface do usuário (UI) reaja adequadamente a entradas inválidas.
 */
data class LoginFormState(
    /**
     * Erro de validação para o nome de usuário, se houver.
     * Este valor é um recurso de string (ID) que pode ser usado para mostrar uma mensagem de erro.
     */
    val usernameError: Int? = null,

    /**
     * Erro de validação para a senha, se houver.
     * Este valor é um recurso de string (ID) que pode ser usado para mostrar uma mensagem de erro.
     */
    val passwordError: Int? = null,

    /**
     * Indica se os dados do formulário são válidos.
     * Se for verdadeiro, significa que tanto o nome de usuário quanto a senha são válidos.
     */
    val isDataValid: Boolean = false
)