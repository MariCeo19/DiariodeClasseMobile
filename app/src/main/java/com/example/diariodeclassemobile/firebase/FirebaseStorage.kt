package com.example.diariodeclassemobile.firebase

import android.net.Uri
import com.example.diariodeclassemobile.model.Aluno
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class FirebaseStorage {

    val firebaseCloudFirestore = FirebaseCloudFirestore()

    fun uploadImageToFirebaseStorage(uriFotoAluno: Uri?, aluno:Aluno){


        val filename = UUID.randomUUID().toString()

        val ref = Firebase.storage.reference.child("/images/$filename")

        val uploadTask = uriFotoAluno?.let { ref.putFile(it) }

        val urlTask = uploadTask?.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

                aluno.linkFoto = task.result.toString()
                firebaseCloudFirestore.salvarAluno(aluno)

            } else {

            }
        }
    }
}