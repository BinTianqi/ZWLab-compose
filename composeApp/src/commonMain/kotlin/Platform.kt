import androidx.compose.material3.ColorScheme

expect fun getPlatform():String

expect fun writeClipBoard(content:String)

expect fun getDynamicTheme():Pair<ColorScheme, ColorScheme>?
