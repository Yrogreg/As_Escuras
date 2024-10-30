package com.example.asescuras.ui.login

/**
 * Classe que representa os detalhes do usuário após autenticação que são expostos para a interface do usuário (UI).
 *
 * Essa classe é utilizada para armazenar as informações do usuário que são necessárias para a UI após a autenticação.
 */
data class LoggedInUserView(
    /**
     * O nome de exibição do usuário, que será exibido na UI.
     */
    val displayName: String
    //... outros campos de dados que podem ser acessíveis para a UI
)