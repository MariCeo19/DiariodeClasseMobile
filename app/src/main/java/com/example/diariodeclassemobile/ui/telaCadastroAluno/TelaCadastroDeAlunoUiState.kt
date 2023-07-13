package com.example.diariodeclassemobile.ui.telaCadastroAluno

import com.example.diariodeclassemobile.model.Aluno

data class TelaCadastroDeAlunoUiState(
    val nome: String = "",
    val curso: String = "",
    val faltas: Int = 0,
    val nota: Int = 0,
    val aluno: Aluno = Aluno()
)
