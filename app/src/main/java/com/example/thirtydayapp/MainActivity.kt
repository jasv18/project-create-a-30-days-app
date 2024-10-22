package com.example.thirtydayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ThirtyDayAppTheme
import com.example.thirtydayapp.data.DessertRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ThirtyDayAppTheme {
                App()
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Main card")
@Composable
fun ThirtyDayAppMainCardPreview() {
    ThirtyDayAppTheme {
        MainCard(dessert = DessertRepository.getAll[0])
    }
}

@Preview(showBackground = true, name = "Dark Main card")
@Composable
fun ThirtyDayAppDarkMainCardPreview() {
    ThirtyDayAppTheme(darkTheme = true) {
        MainCard(dessert = DessertRepository.getAll[3])
    }
}

@Preview(showBackground = true, name = "Light theme app", showSystemUi = true)
@Composable
fun ThirtyDayLightAppPreview() {
    ThirtyDayAppTheme {
        App()
    }
}

@Preview(showBackground = true, name = "Dark theme app", showSystemUi = true)
@Composable
fun ThirtyDayDarkAppPreview() {
    ThirtyDayAppTheme(darkTheme = true) {
        App()
    }
}