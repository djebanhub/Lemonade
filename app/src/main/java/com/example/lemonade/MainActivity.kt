package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF9E71E)
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        lemonWithButtonAndImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
fun lemonWithButtonAndImage(modifier: Modifier = Modifier) {
    // État actuel de l'application (1 à 4 )
    var currentStep by remember { mutableIntStateOf(1) }

    // Compteur pour l'étape de pressage
    var squeezeCount by remember { mutableIntStateOf(0) }

    // Nombre de pressions nécessaires (généré aléatoirement pour chaque nouveau citron)
    var squeezeCountNeeded by remember { mutableIntStateOf((2..4).random()) }

    // Ressources selon l'étape actuelle
    val imageResource = when(currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val textResource = when(currentStep) {
        1 -> R.string.lemonade_select
        2 -> R.string.lemonade_squeeze
        3 -> R.string.lemonade_drink
        else -> R.string.empty_glass_restart
    }

    val contentDescriptionResource = when(currentStep) {
        1 -> R.string.lemon_tree_content_description
        2 -> R.string.lemon_content_description
        3 -> R.string.glass_of_lemonade_content_description
        else -> R.string.empty_glass_content_description
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image cliquable
        Image(
            painter = painterResource(imageResource),
            contentDescription = stringResource(contentDescriptionResource),
            modifier = Modifier
                .size(250.dp)
                .background(
                    color = Color(0xFFC3E9DC), // Couleur de fond légèrement bleue
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
                .clickable {
                    // Gestion des clics selon l'étape actuelle
                    when(currentStep) {
                        1 -> {
                            // Étape 1 → Étape 2
                            currentStep = 2
                            squeezeCount = 0
                            squeezeCountNeeded = (2..4).random()
                        }

                        2 -> {
                            // Étape 2 → Incrémenter le compteur de pressions
                            squeezeCount++
                            if (squeezeCount >= squeezeCountNeeded) {
                                // Assez de pressions → Étape 3 (Boire)
                                currentStep = 3
                            }
                            // Sinon, rester à l'étape 2
                        }

                        3 -> {
                            // Étape 3 → Étape 4 (Boire → Verre vide)
                            currentStep = 4
                        }

                        4 -> {
                            // Étape 4 → Étape 1 (Recommencer le cycle)
                            currentStep = 1
                        }
                    }
                }
        )
        // Texte d'instruction
        Text(
            text = stringResource(textResource),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        // Texte de debug
        if (currentStep == 2) {
            Text(
                text = "Pressions: $squeezeCount/$squeezeCountNeeded",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        LemonadeApp()
       }
}