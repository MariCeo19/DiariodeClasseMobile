package com.example.diariodeclassemobile.ui.telaCadastroAluno

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.diariodeclassemobile.R

@Composable
fun TelaCadastroAluno(
    controleDeNavegacao: NavHostController,
    telaCadastroDeAlunoViewModel: TelaCadastroDeAlunoViewModel = viewModel()
) {

    var photoUri: Uri? by remember { mutableStateOf(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Cadastrar Aluno",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .padding(20.dp)
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {

                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = photoUri)
                        .build()
                )

                if (photoUri == null) {
                    ImagemLogin(
                        painter = painterResource(id = R.drawable.login),
                        launcher = launcher
                    )
                } else {
                    ImagemLogin(
                        painter = painter,
                        launcher = launcher
                    )
                }

                OutlinedTextField(
                    value = telaCadastroDeAlunoViewModel.nome,
                    onValueChange = {
                        telaCadastroDeAlunoViewModel.updateNome(it)
                    },
                    label = { Text(text = "Nome") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                )

                OutlinedTextField(
                    value = telaCadastroDeAlunoViewModel.curso,
                    onValueChange = {
                        telaCadastroDeAlunoViewModel.updateCurso(it)
                    },
                    label = { Text(text = "Curso") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                )

                Button(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    onClick = {
                        if (photoUri != null) {

                            telaCadastroDeAlunoViewModel.salvarAluno(photoUri)

                            telaCadastroDeAlunoViewModel.limparCampos()
                            controleDeNavegacao.popBackStack()
                        }
                    }
                ) {
                    Text(text = "Salvar")
                }

            }
        }
    }

}

@Composable
fun ImagemLogin(
    painter: Painter,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .clickable {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        contentScale = ContentScale.Crop,
    )
}


