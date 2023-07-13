package com.example.diariodeclassemobile.dados

import com.example.diariodeclassemobile.R
import com.example.diariodeclassemobile.model.Aluno

public class ListaDeAlunos {
    fun carregarListaDeAlunos():List<Aluno>{
        return listOf<Aluno>(
            Aluno("Rafael", "Eletronica"),
            Aluno("Gislene", "ADM"),
            Aluno("Lara", "Designer"),
        )
    }
}