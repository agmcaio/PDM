package com.example.galeriadearte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.basiccompose.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtGalleryTheme {
                ArtGalerryPreview()
            }
        }
    }
}


data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

@Composable
fun Layout() {

    val artworks = listOf(
        Artwork(R.drawable.vangogh, "A noite estrelada", "Vincent Van Gogh", "1889"),
        Artwork(R.drawable.totenart, "Auto-Retrato", "Pablo Picasso", "1907"),
        Artwork(R.drawable.monalisa, "Mona Lisa", "Leonardo da Vinci", "1503")
    )

    // Estados para elementos dinâmicos
    var currentIndex by remember { mutableIntStateOf(0) }

    val currentArtwork = artworks[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = currentArtwork.imageRes),
            contentDescription = "Arte Imagem",
            modifier = Modifier
                .size(300.dp)
                .background(Color.White)
                .padding(10.dp)
        )
        Text(
            text = currentArtwork.title,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = currentArtwork.artist,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = "(" + currentArtwork.year + ")")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                currentIndex = if (currentIndex > 0) currentIndex - 1 else artworks.size - 1
            }) {
                Text(text = "Previous")
            }
            Button(onClick = {
                currentIndex = if (currentIndex < artworks.size - 1) currentIndex + 1 else 0
            }) {
                Text(text = "Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalerryPreview() {
    ArtGalleryTheme {
        Layout()
    }
}