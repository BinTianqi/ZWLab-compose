import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication, title = "Zero width Lab", state = state
    ) {
        App()
    }
}