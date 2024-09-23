package com.example.navegacao1.ui.telas

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navegacao1.model.dados.UsuarioDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TelaLogin(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(value = login, onValueChange = { login = it }, label = { Text(text = "Login") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = senha, visualTransformation = PasswordVisualTransformation(),
            onValueChange = { senha = it }, label = { Text(text = "Senha") })

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            scope.launch(Dispatchers.IO) {
                UsuarioDAO().buscarPorNome(login) { usuario ->
                    if (usuario != null && usuario.senha == senha) {
                        navController.navigate("principal")
                    } else {
                        mensagemErro = "Login ou senha inválidos!"
                    }
                }
            }
        }) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            navController.navigate("cadastro")
        }) {
            Text("Cadastrar")
        }

        mensagemErro?.let {
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                mensagemErro = null
            }
        }
    }
}