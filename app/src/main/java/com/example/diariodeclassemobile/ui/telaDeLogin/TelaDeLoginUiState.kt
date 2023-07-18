package com.example.diariodeclassemobile.ui.telaDeLogin

import com.example.diariodeclassemobile.R

data class TelaDeLoginUiState (
    val fotoLogin:Int = R.drawable.login,
    val email:String = "",
    val senha:String = "",
    val confirmarSenha:String = "",
    val status:Boolean = false,
    val emailSenhaIncorretos:Boolean = false,
    val labelSenha:String = "Senha",
    val cadastro:Boolean = false
)