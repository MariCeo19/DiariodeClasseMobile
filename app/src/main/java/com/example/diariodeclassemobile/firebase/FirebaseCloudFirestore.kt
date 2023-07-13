package com.example.diariodeclassemobile.firebase

import android.util.Log
import com.example.diariodeclassemobile.model.Aluno
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow

class FirebaseCloudFirestore {

    private val db = FirebaseFirestore.getInstance()
    private val _listaDeAlunos = MutableStateFlow<MutableList<Aluno>>(mutableListOf())
    private val listaDeAlunos: StateFlow<MutableList<Aluno>> = _listaDeAlunos

    fun salvarAluno(aluno: Aluno){

        db.collection("Alunos").document().set(aluno)

    }

    fun carregarListaDeAlunos(): Flow<MutableList<Aluno>> {

        val carregaListaDeAlunos:MutableList<Aluno> = mutableListOf()

        db.collection("Alunos").get().addOnCompleteListener{querySnapshot ->
            if(querySnapshot.isSuccessful) {
                for (documento in querySnapshot.result)
                {
                    val aluno = documento.toObject(Aluno::class.java)
                    carregaListaDeAlunos.add(aluno)
                }
            }
            _listaDeAlunos.value = carregaListaDeAlunos
        }

        return listaDeAlunos
    }
}