package com.example.diariodeclassemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diariodeclassemobile.telas.TelaListaDeAlunos
import com.example.diariodeclassemobile.telas.TelaLogin
import com.example.diariodeclassemobile.ui.telaCadastroAluno.TelaCadastroAluno
import com.example.diariodeclassemobile.ui.theme.DiarioDeClasseMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiarioDeClasseMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppDiarioDeClasse()
                }
            }
        }
    }
}
@Composable
fun AppDiarioDeClasse(){

    var controleDeNavegacao = rememberNavController()

    NavHost(
        navController = controleDeNavegacao,
        startDestination =  "telaLogin"
    ){
        composable(
            route = "telaLogin"
        ){
            TelaLogin(controleDeNavegacao)
        }
        composable(
            route = "telaListaDeAlunos"
        ){
            TelaListaDeAlunos(controleDeNavegacao)
        }
        composable(
            route = "telaCadastroDeAluno"
        ){
            TelaCadastroAluno(controleDeNavegacao)
        }
    }

}