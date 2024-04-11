import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun writeClipBoard(content: String) {
    try{
        val stringSelection = StringSelection(content)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(stringSelection,null)
    }catch(e:Exception){
        System.out.println(e)
    }
}
