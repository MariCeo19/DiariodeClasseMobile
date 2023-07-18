package com.example.diariodeclassemobile.ui.telaDeLogin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TelaDeLoginViewModel:ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private val _telaDeLoginUiState = MutableStateFlow(TelaDeLoginUiState())
    val telaDeLoginUiState: StateFlow<TelaDeLoginUiState> = _telaDeLoginUiState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var senha by mutableStateOf("")
        private set

    var confirmarSenha by mutableStateOf("")
        private set

    fun updateEmail(email: String) {
        this.email = email
    }
    fun updateSenha(senha: String) {
        this.senha = senha
    }
    fun updateConfirmarSenha(confirmarSenha: String) {
        this.confirmarSenha = confirmarSenha
    }

    fun updateLimparCamposSenha(){
        this.senha = ""
        this.confirmarSenha = ""
    }

    fun updateLogin(
        logado:Boolean=false,
        bordaVermelha:Boolean=false,
        cadastro:Boolean=false
    ){
        _telaDeLoginUiState.update { currentState ->
            currentState.copy(
                status = logado,
                emailSenhaIncorretos = bordaVermelha,
                cadastro = cadastro
            )
        }
    }
    fun logarUsuario(){

        if (email.isNullOrEmpty() || senha.isNullOrEmpty()) {

            updateLogin(false,true)
            return
        }
        val task = this.auth.signInWithEmailAndPassword(email, senha)
        task.addOnSuccessListener {
            updateLogin(true,false)
            updateLimparCamposSenha()
        }
        .addOnFailureListener {
            updateLogin(false,true)
            updateLimparCamposSenha()
        }
    }

    fun criarUsuario() {

        if (email.isNullOrEmpty() || senha.isNullOrEmpty() || confirmarSenha.isNullOrEmpty()) {
            updateLogin(false,true,cadastro = true)
            return
        }
        if(!senha.equals(confirmarSenha)){
            updateLogin(false,true,cadastro = true)
            updateLimparCamposSenha()
            return
        }
        val task = auth.createUserWithEmailAndPassword(email, senha)
        task.addOnSuccessListener {
            updateLogin(cadastro = false, bordaVermelha = false)
            updateLimparCamposSenha()
        }
        .addOnFailureListener {
            updateLogin(false,true,cadastro = true)
            updateLimparCamposSenha()
        }
    }

}