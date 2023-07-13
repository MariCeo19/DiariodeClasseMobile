package com.example.diariodeclassemobile.ui.telaCadastroAluno

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diariodeclassemobile.firebase.FirebaseCloudFirestore
import com.example.diariodeclassemobile.firebase.FirebaseStorage
import com.example.diariodeclassemobile.model.Aluno
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaCadastroDeAlunoViewModel:ViewModel() {


    private val _telaCadastroDeAlunoUiState = MutableStateFlow(TelaCadastroDeAlunoUiState())
    val telaCadastroDeAlunoUiState: StateFlow<TelaCadastroDeAlunoUiState> = _telaCadastroDeAlunoUiState.asStateFlow()

    var nome by mutableStateOf("")
        private set
    var curso by mutableStateOf("")
        private set
    var faltas by mutableStateOf(0)
        private set
    var nota by mutableStateOf(0)
        private set


    fun updateNome(nome: String) {
        this.nome = nome
    }
    fun updateCurso(curso: String) {
        this.curso = curso
    }
    fun updateFaltas(faltas: Int) {
        this.faltas = faltas
    }
    fun updateNota(nota: Int) {
        this.nota = nota
    }

    fun salvarAluno(photoUri: Uri?) {

        FirebaseStorage().uploadImageToFirebaseStorage(photoUri, Aluno(nome = nome,curso = curso))
    }

    fun limparCampos(){
        this.nome = ""
        this.curso = ""
    }

    fun salvarAluno(aluno: Aluno, photoUri: Uri?){

        FirebaseCloudFirestore().salvarAluno(aluno)
    }


}