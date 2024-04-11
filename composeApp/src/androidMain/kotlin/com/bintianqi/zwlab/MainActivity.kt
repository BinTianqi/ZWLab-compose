package com.bintianqi.zwlab

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

var writeClipBoardContent = ""

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        setContent {
            LaunchedEffect(Unit){
                while(true){
                    if(writeClipBoardContent!=""){
                        copyToClipBoard(context, writeClipBoardContent)
                        writeClipBoardContent = ""
                    }
                    delay(200)
                }
            }
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}