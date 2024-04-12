import androidx.compose.material3.ColorScheme
import com.bintianqi.zwlab.dynamicTheme
import com.bintianqi.zwlab.writeClipBoardContent

actual fun writeClipBoard(content: String) {
    writeClipBoardContent = content
}

actual fun getPlatform() = "android"
actual fun getDynamicTheme(): Pair<ColorScheme, ColorScheme>? {
    return dynamicTheme
}