package com.example.diariodeclassemobile.ui.TelaListaDeAlunos

import com.example.diariodeclassemobile.model.Aluno

data class TelaListaDeAlunosUiState(
    val listaDeAlunos:MutableList<Aluno> = mutableListOf()

)
