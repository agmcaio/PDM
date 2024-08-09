package com.example.gyment

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gyment.ui.theme.GymentTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymentTheme {
                MyApp(

                )
            }
        }
    }
}

data class Exercise(val title: String, val imageResId: Int)

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController = navController) }
        composable("dashboard") { DashboardScreen() }
        composable("signup") { SignUpScreen(navController = navController) }
    }
}

@Composable
fun LoginScreen(navController: NavController, modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val mockEmail = "maromba@email.com"
    val mockPassword = "123"

    val backgroundImage: Painter = painterResource(id = R.drawable.background)
    val colorLabel = Color(0xFF7C7C8A)
    val buttonColor = Color(0xFF00875F)

    Box(
       modifier = modifier
           .fillMaxSize()
    ){
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )

        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(bottom = 0.dp)
                    .size(200.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(Color(0xFFFFFFFF)),
                label = { Text("Email", color = colorLabel) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha", color = colorLabel) },
                textStyle = TextStyle(Color(0xFFFFFFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (email == mockEmail && password == mockPassword) {
                            navController.navigate("dashboard")
                        } else {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar("Dados incorretos. Tente novamente.")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Entrar")
            }

            Button(
                onClick = {
                    navController.navigate("signup")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Cadastrar")
            }
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun DashboardScreen() {
    val exercises = remember {
        listOf(
            Exercise("Puxada Frontal", R.drawable.puxadafrontal),
            Exercise("Remada Curvada", R.drawable.remadacurvada),
            Exercise("Remada Unilateral", R.drawable.remadaunilateral),
            Exercise("Levantamento Terra", R.drawable.levantamentoterra)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exercises.size) { exercise ->
                ExerciseCard(exercise = exercises.get(exercise))
            }
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = exercise.imageResId),
                contentDescription = exercise.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exercise.title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    val backgroundImage: Painter = painterResource(id = R.drawable.background)
    val colorLabel = Color(0xFF7C7C8A)
    val buttonColor = Color(0xFF00875F)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = backgroundImage,
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(bottom = 0.dp)
                    .size(200.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                textStyle = TextStyle(Color(0xFFFFFFFF)),
                label = { Text("Nome", color = colorLabel) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(Color(0xFFFFFFFF)),
                label = { Text("Email", color = colorLabel) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha", color = colorLabel) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Confirmar Senha", color = colorLabel) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text("Criar e Acessar")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    GymentTheme {
        MyApp()
    }
}