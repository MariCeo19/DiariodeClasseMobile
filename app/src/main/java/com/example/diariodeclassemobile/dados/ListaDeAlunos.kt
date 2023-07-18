package com.example.diariodeclassemobile.dados

import com.example.diariodeclassemobile.R
import com.example.diariodeclassemobile.model.Aluno

public class ListaDeAlunos {
    fun carregarListaDeAlunos():List<Aluno>{
        return listOf<Aluno>(
            Aluno("Karolyne", "Eletronica"),
            Aluno("João", "ADM"),
            Aluno("Rebeka", "Designer"),
            Aluno("Cristina", "ADM"),
            Aluno("Roberta", "Eletronica"),
        )
    }
}