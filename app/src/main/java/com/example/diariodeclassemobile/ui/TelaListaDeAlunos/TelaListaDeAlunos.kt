package com.example.diariodeclassemobile.telas

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.diariodeclassemobile.R
import com.example.diariodeclassemobile.model.Aluno
import com.example.diariodeclassemobile.ui.TelaListaDeAlunos.TelaListaDeAlunosViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaDeAlunos(
    controleDeNavegacao: NavHostController,
    telaListaDeAlunosViewModel: TelaListaDeAlunosViewModel = viewModel()
) {
    val listaDeAlunos = telaListaDeAlunosViewModel
        .carregarListaDeAlunos()
        .collectAsState(mutableListOf())
        .value

    Scaffold(
        modifier = Modifier
            .padding(10.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    controleDeNavegacao.navigate("telaCadastroDeAluno")
                }) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                    contentDescription = null
                )
            }
        },
        content = { contentPadding ->
            teste(
                listaDeAlunos,
                contentPadding
            )
        },
        topBar = {
            TopAppBar(
                title = {Text(text = "Lista de Alunos")},
                navigationIcon = {
                    IconButton(onClick = {
                        controleDeNavegacao.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    )
}

@Composable
fun teste(
    listaDeAlunos: MutableList<Aluno>,
    contentPadding: PaddingValues
) {
    LazyColumn(
        Modifier.padding(contentPadding)
    ) {
        items(listaDeAlunos) { aluno ->
            CardAluno(aluno = aluno)
        }
    }
}

@Composable
fun CardAluno(
    aluno: Aluno
) {
    var expandir by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FotoPerfil(aluno.linkFoto)
                DadosMatriculaAluno(aluno.nome, aluno.curso)
                BotaoExpandirDadosAluno(expandir, { expandir = !expandir })
            }
            if (expandir) {
                DadosRendimentoAluno(aluno.nota, aluno.faltas)
            }
        }
    }

}


@Composable
fun FotoPerfil(
    urlImagem: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(urlImagem)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.login),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .border(
                BorderStroke(4.dp, Color.Black),
                CircleShape
            )
            .clip(CircleShape),
    )
}

@Composable
fun DadosMatriculaAluno(
    nome: String,
    curso: String
) {
    Column(
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = nome,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = curso,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun DadosRendimentoAluno(
    nota: Int,
    faltas: Int
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Nota: $nota",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Faltas: $faltas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BotaoExpandirDadosAluno(
    expandir: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector =
            if (expandir)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun CardAlunoPreview() {
    Column() {
        Aluno("Rafael", "Eletronica")
    }
}