import androidx.compose.material3.ColorScheme
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

actual fun getPlatform() = "desktop"

actual fun writeClipBoard(content: String) {
    try{
        val stringSelection = StringSelection(content)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(stringSelection,null)
    }catch(e:Exception){
        println(e)
    }
}

actual fun getDynamicTheme():Pair<ColorScheme, ColorScheme>? = null
