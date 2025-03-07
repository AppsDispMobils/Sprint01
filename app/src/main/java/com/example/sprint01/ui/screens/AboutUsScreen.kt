package com.example.sprint01.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sprint01.R

@Composable
fun AboutUsScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "About Us",
            style = TextStyle(fontSize = 32.sp, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Descripción de la app
        Text(
            text = stringResource(id = R.string.about_us1),
            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.about_us2),
            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Un botón back homeScreen
        Button(
            onClick = { navController.navigate("home")},
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "Go Back")
        }
    }
}