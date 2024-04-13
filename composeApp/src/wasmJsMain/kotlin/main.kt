import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget"){
        var dark by remember{mutableStateOf(false)}
        var inited by remember{mutableStateOf(false)}
        if(!inited){dark = isSystemInDarkTheme(); inited = true}
        App(dark){dark=it}
    }
}