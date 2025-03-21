package com.example.sprint01.ui.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sprint01.R
import com.example.sprint01.ui.viewmodel.SettingsViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: SettingsViewModel = viewModel()  // Obtener el ViewModel
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    val selectedLanguage by remember { mutableStateOf(sharedPreferences.getString("language", "es") ?: "es") }

    // Configuración de idioma
    val language = viewModel.language // Obtenemos el idioma desde el ViewModel

    Spacer(modifier = Modifier.height(30.dp))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = stringResource(id = R.string.settings), modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp, textAlign = TextAlign.Center)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = 0,
                navController
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Opciones de configuración
                Spacer(modifier = Modifier.height(10.dp))

                // Opción para cambiar la contraseña
                Text(
                    text = stringResource(id = R.string.change_password),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clickable { /* TODO: Implementar */ }
                        .fillMaxWidth()
                        .padding(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Cambiar idioma
                LanguageDropdown(
                    selectedLanguage = language,
                    onLanguageSelected = { newLang ->
                        viewModel.updateLanguage(newLang) // Actualizamos el idioma en el ViewModel
                        sharedPreferences.edit().putString("language", newLang).apply() // Guardamos en SharedPreferences
                        LocaleHelper.setLocale(context, newLang) // Aplicamos el cambio de idioma
                        restartApp(context) // Reiniciar la aplicación
                    },
                    availableLanguages = listOf("es", "en")
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botón para volver a la Home Screen
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.volver))
                }
            }
        }
    )
}

@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    availableLanguages: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val languageDisplay = when (selectedLanguage) {
        "es" -> "Español"
        "en" -> "English"
        else -> selectedLanguage
    }

    OutlinedTextField(
        value = languageDisplay,
        onValueChange = {},
        label = { Text("Idioma") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Mostrar idiomas")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        availableLanguages.forEach { lang ->
            val langName = when (lang) {
                "es" -> "Español"
                "en" -> "English"
                else -> lang
            }
            DropdownMenuItem(
                text = { Text(langName) },
                onClick = {
                    onLanguageSelected(lang) // Llamamos a la función para seleccionar el idioma
                    expanded = false
                }
            )
        }
    }
}

// Helper functions to handle Locale changes
object LocaleHelper {
    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.createConfigurationContext(config)
        }
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}

fun restartApp(context: Context) {
    val intent = Intent(context, context.javaClass)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    context.startActivity(intent)
}
