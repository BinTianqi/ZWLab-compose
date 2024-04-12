import com.bintianqi.zwlab.writeClipBoardContent

actual fun writeClipBoard(content: String) {
    writeClipBoardContent = content
}

actual fun getPlatform() = "android"