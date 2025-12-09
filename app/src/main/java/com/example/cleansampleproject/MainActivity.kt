package com.example.cleansampleproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.cleansampleproject.presentation.navigation.AppNavigation
import com.example.cleansampleproject.ui.theme.CleanSampleProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanSampleProjectTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}