package com.example.diariodeclassemobile.ui.TelaListaDeAlunos

import androidx.lifecycle.ViewModel
import com.example.diariodeclassemobile.firebase.FirebaseCloudFirestore
import com.example.diariodeclassemobile.model.Aluno
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TelaListaDeAlunosViewModel:ViewModel() {

    private val _telaListaDeAlunosUiState = MutableStateFlow(TelaListaDeAlunosUiState())
    val telaListaDeAlunosUiState: StateFlow<TelaListaDeAlunosUiState> = _telaListaDeAlunosUiState.asStateFlow()

    private val firebaseCloudFirestore = FirebaseCloudFirestore()

    fun carregarListaDeAlunos(): Flow<MutableList<Aluno>> {
        return firebaseCloudFirestore.carregarListaDeAlunos()
    }

}