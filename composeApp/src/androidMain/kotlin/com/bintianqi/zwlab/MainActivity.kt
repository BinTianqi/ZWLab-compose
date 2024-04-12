package com.bintianqi.zwlab

import DecodeZW
import EncodeZW
import InsertZW
import NavBar
import RemoveZW
import ZWList
import android.app.Activity
import android.os.Build.VERSION
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ui.getTheme

var writeClipBoardContent = ""
var dynamicTheme:Pair<ColorScheme, ColorScheme>? = null

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val context = applicationContext
        setContent {
            var inited by remember{mutableStateOf(false)}
            var dark by remember{mutableStateOf(false)}
            if(!inited){ dark = isSystemInDarkTheme(); inited = true}
            enableEdgeToEdge()
            LaunchedEffect(Unit){
                while(true){
                    if(writeClipBoardContent!=""){
                        copyToClipBoard(context, writeClipBoardContent)
                        writeClipBoardContent = ""
                    }
                    delay(200)
                }
            }
            if(VERSION.SDK_INT>=31){
                dynamicTheme = Pair(dynamicLightColorScheme(context), dynamicDarkColorScheme(context))
            }
            val view = LocalView.current
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !dark
            }
            MaterialTheme(
                colorScheme = getTheme(dark)
            ){
                AppContent(dark){dark = it}
            }
        }
    }
}

@Composable
private fun AppContent(dark:Boolean, changeTheme:(Boolean)->Unit){
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            NavBar(backStackEntry?.destination?.route?:"Home"){
                if(it!=backStackEntry?.destination?.route){
                    navController.navigate(it){
                        popUpTo("Home"){
                            inclusive = false
                        }
                    }
                }
            }
        },
        modifier = Modifier.background(colorScheme.background).statusBarsPadding()
    ){paddingValues->
        NavHost(
            navController = navController,
            startDestination = "Home"
        ){
            composable(route = "Insert"){ InsertZW(paddingValues) }
            composable(route = "Remove"){ RemoveZW(paddingValues) }
            composable(route = "Home"){ ZWList(paddingValues, dark, changeTheme) }
            composable(route = "Encode"){ EncodeZW(paddingValues) }
            composable(route = "Decode"){ DecodeZW(paddingValues) }
        }
    }
}
