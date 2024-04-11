import android.os.Build
import com.bintianqi.zwlab.writeClipBoardContent

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun writeClipBoard(content: String) {
    writeClipBoardContent = content
}
