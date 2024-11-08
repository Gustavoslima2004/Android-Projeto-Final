package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

// Define as telas do app
sealed class Screen {
    data object Login : Screen()
    data object MainScreen : Screen()
}

@Composable
fun MyApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        is Screen.Login -> LoginScreen(onLoginSuccess = { currentScreen = Screen.MainScreen })
        is Screen.MainScreen -> MainScreen()
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
                .padding(8.dp),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (username.text.isEmpty()) {
                    Text("Nome de Usuário", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
                .padding(8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                if (password.text.isEmpty()) {
                    Text("Senha", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.text == "admin" && password.text == "1234") {
                    onLoginSuccess()
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Credenciais incorretas")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Entrar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun MainScreen()
    {
        var text1 by remember { mutableStateOf(TextFieldValue("")) }
        var isChecked1 by remember { mutableStateOf(false) }

        var text2 by remember { mutableStateOf(TextFieldValue("")) }
        var isChecked2 by remember { mutableStateOf(false) }

        var text3 by remember { mutableStateOf(TextFieldValue("")) }
        var isChecked3 by remember { mutableStateOf(false) }

        var text4 by remember { mutableStateOf(TextFieldValue("")) }
        var isChecked4 by remember { mutableStateOf(false) }

        var hour1 by remember { mutableStateOf(TextFieldValue("")) }
        var hour2 by remember { mutableStateOf(TextFieldValue("")) }
        var isDay by remember { mutableStateOf(true) }

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        var isMetasDefinidas by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val backgroundImage = if (isDay) {
                R.drawable.background1
            } else {
                R.drawable.background2
            }


            Crossfade(targetState = backgroundImage, label = "") { image ->
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { snackbarData ->
                        val backgroundColor = when (snackbarData.visuals.message) {
                            "Objetivo: 1 Concluído!" -> Color(0xCD3DE211)
                            "Objetivo: 2 Concluído!" -> Color(0xCD3DE211)
                            "Objetivo: 3 Concluído!" -> Color(0xCD3DE211)
                            "Objetivo: 4 Concluído!" -> Color(0xCD3DE211)

                            "Metas definidas com sucesso!" -> Color(0xCD3DE211)
                            "Horário inválido: deve estar entre 00h e 23h." -> Color(0xEBFFDD00)
                            else -> Color(0xB4CE0505)
                        }
                        Snackbar(
                            snackbarData = snackbarData,
                            modifier = Modifier.background(backgroundColor),
                            containerColor = backgroundColor,
                            contentColor = Color.White
                        )
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )


                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(50.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Metas do dia: ",
                        color = Color.White,
                        fontSize = 25.sp,
                    )
                }

                // Botões "Dia" e "Noite"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { isDay = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDay) Color(0xFF0A98D6) else Color(0xFFFFE91A)
                        )
                    ) {
                        Text("Dia")
                    }
                    Button(
                        onClick = { isDay = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isDay) Color(0xF30A98D6) else Color(0xFF1E2329)
                        )
                    ) {
                        Text("Noite")
                    }
                }

                // Implemente o codigo aqui

                val texto = if (isDay) {
                    Color.Black
                }else{
                    Color.White
                }

                val day = if (isDay) "am" else "pm"
                val night = if (isDay) "pm" else "am"





                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(100.dp))
                            .clip(RoundedCornerShape(50.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = hour1.text.padStart(2, '0'),
                            onValueChange = {
                                val formatted = it.filter { char -> char.isDigit() }.take(2)
                                hour1 = TextFieldValue(formatted)
                            },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 24.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "h - $day" ,
                        color = texto,
                        fontSize = 24.sp
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(100.dp))
                            .clip(RoundedCornerShape(50.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicTextField(
                            value = hour2.text.padStart(2, '0'),
                            onValueChange = {
                                val formatted = it.filter { char -> char.isDigit() }.take(2)
                                hour2 = TextFieldValue(formatted)
                            },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 24.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "h - $night",
                        color = texto,
                        fontSize = 24.sp
                    )
                }




                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(300.dp)
                        .background(Color.DarkGray)
                        .padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = text1,
                                onValueChange = { text1 = it },
                                label = { Text("Objetivo: 1") },
                                modifier = Modifier.weight(1f)
                                    .background(Color.Black)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = isChecked1,
                                onCheckedChange = {
                                    if (isMetasDefinidas) {
                                        isChecked1 = it
                                        if (it) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Objetivo: 1 Concluído!")
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = text2,
                                onValueChange = { text2 = it },
                                label = { Text("Objetivo: 2") },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = isChecked2,
                                onCheckedChange = {
                                    if (isMetasDefinidas) {
                                        isChecked2 = it
                                        if (it) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Objetivo: 2 Concluído!")
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = text3,
                                onValueChange = { text3 = it },
                                label = { Text("Objetivo: 3") },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = isChecked3,
                                onCheckedChange = {
                                    if (isMetasDefinidas) {
                                        isChecked3 = it
                                        if (it) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Objetivo: 3 Concluído!")
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = text4,
                                onValueChange = { text4 = it },
                                label = { Text("Objetivo: 4") },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Switch(
                                checked = isChecked4,
                                onCheckedChange = {
                                    if (isMetasDefinidas) {
                                        isChecked4 = it
                                        if (it) {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Objetivo: 4 Concluído!")
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        val hour1Int = hour1.text.toIntOrNull()
                        val hour2Int = hour2.text.toIntOrNull()

                        when {
                            text1.text.isEmpty() || text2.text.isEmpty() || text3.text.isEmpty() || text4.text.isEmpty() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Operação inválida: Todos os campos devem ser preenchidos.")
                                }
                            }

                            hour1Int == null || hour2Int == null || hour1Int !in 0..23 || hour2Int !in 0..23 -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Horário inválido: deve estar entre 00h e 23h.")
                                }
                            }

                            else -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Metas definidas com sucesso!")
                                    isMetasDefinidas = true
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03A9F4),
                        contentColor = Color.White
                    )
                ) {
                    Text("Definir Metas")
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
