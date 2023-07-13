package com.example.diariodeclassemobile.telas

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.android.volley.toolbox.ImageRequest
import com.example.diariodeclassemobile.R
import com.example.diariodeclassemobile.ui.telaDeLogin.TelaDeLoginUiState
import com.example.diariodeclassemobile.ui.telaDeLogin.TelaDeLoginViewModel


@Composable
fun TelaLogin(
    controleDeNavegacao: NavHostController,
    telaDeLoginViewModel: TelaDeLoginViewModel = viewModel()
){

    val context = LocalContext.current

    val telaDeLoginUiState by telaDeLoginViewModel.telaDeLoginUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                ImagemLogin(telaDeLoginUiState)

                CamposDeTexto(
                    telaDeLoginViewModel,
                    telaDeLoginUiState
                )
                BotaoLogin(
                    telaDeLoginViewModel,
                    context,
                    controleDeNavegacao,
                    telaDeLoginUiState
                )
            }
        }
        if(!telaDeLoginUiState.cadstro) {
            Text(
                text = "Criar nova conta",
                modifier = Modifier.clickable {
                    telaDeLoginViewModel.updateLogin(cadastro = true)
                }
            )
        }
    }
}

@Composable
private fun BotaoLogin(
    telaDeLoginViewModel: TelaDeLoginViewModel,
    context: Context,
    controleDeNavegacao: NavHostController,
    telaDeLoginUiState: TelaDeLoginUiState
) {
    Button(
        onClick = {
            if(telaDeLoginUiState.cadstro)
                telaDeLoginViewModel.criarUsuario()
            else
                telaDeLoginViewModel.logarUsuario()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text =
            if(telaDeLoginUiState.cadstro)
                "Cadastrar"
            else
                "Entrar"
        )
    }

    if (telaDeLoginUiState.status) {
        telaDeLoginViewModel.updateLogin()
        Toast.makeText(context, "sucesso", Toast.LENGTH_SHORT).show()
        controleDeNavegacao.navigate("telaListaDeAlunos")
    }
}

@Composable
fun ImagemLogin(
    telaDeLoginUiState: TelaDeLoginUiState
) {

    Image(
        painter = painterResource(id = telaDeLoginUiState.fotoLogin),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp)
            .clickable {

        }
    )
}

@Composable
fun CamposDeTexto(
    telaDeLoginViewModel: TelaDeLoginViewModel,
    telaDeLoginUiState: TelaDeLoginUiState
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = telaDeLoginViewModel.email,
        onValueChange = {
            telaDeLoginViewModel.updateEmail(it)
            telaDeLoginViewModel.updateLogin(cadastro = telaDeLoginUiState.cadstro)
        },
        label =  { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType =KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Down)
                focusManager.clearFocus()
            }
        ),
        isError =  telaDeLoginUiState.emailSenhaIncorretos
    )

    OutlinedTextField(
        value = telaDeLoginViewModel.senha,
        onValueChange = {
            telaDeLoginViewModel.updateSenha(it)
            telaDeLoginViewModel.updateLogin(cadastro = telaDeLoginUiState.cadstro)
        },
        label = { Text(text = "Senha") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType =KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Down)
                focusManager.clearFocus()
            }
        ),
        isError = telaDeLoginUiState.emailSenhaIncorretos
    )

    if(telaDeLoginUiState.cadstro) {

        OutlinedTextField(
            value = telaDeLoginViewModel.confirmarSenha,
            onValueChange = {
                telaDeLoginViewModel.updateConfirmarSenha(it)
                telaDeLoginViewModel.updateLogin(cadastro = telaDeLoginUiState.cadstro)
            },
            label = { Text(text = "Confirmar Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType =KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = telaDeLoginUiState.emailSenhaIncorretos
        )
    }
}

