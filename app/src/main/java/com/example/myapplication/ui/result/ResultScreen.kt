package com.example.myapplication.ui.result

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    score: Int,
    navigateToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // BEIGE
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "END OF THE GAME",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your score",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )

        Text(
            text = "$score",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground // NEGRO
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {

                val shareText = "I just got $score points in Trivial Pursuit! Can you beat me?"

                //creamos un Intent de tipo ACTION_SEND (para enviar datos a otra app)
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    //le adjuntamos nuestro mensaje de texto
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    //le decimos al sistema que el contenido es texto plano
                    type = "text/plain"
                }

                // creamos un "Chooser" (Selector).
                //esto fuerza a que aparezca el menú nativo de Android preguntando:
                //"¿Con qué aplicación quieres compartir esto?" (WhatsApp, Mail, Mensajes...)
                val shareIntent = Intent.createChooser(sendIntent, "Share your score")

                //lanzamos la actividad usando el contexto que guardamos al principio
                context.startActivity(shareIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary // Lila
            )
        ) {
            Text(text = "SHARE SCORE", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = navigateToMenu,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.tertiary // Rosa Return
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
        ) {
            Text(text = "RETURN TO MENU", fontWeight = FontWeight.Bold)
        }
    }
}