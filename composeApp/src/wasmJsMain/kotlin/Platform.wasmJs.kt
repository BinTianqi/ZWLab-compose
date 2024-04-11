class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun writeClipBoard(content: String) {
    js("""
        document.body.addEventListener(
            'click',
            async () => {
                try {
                    await navigator.clipboard.writeText(content);
                    console.log('Page URL copied to clipboard');
                } catch (err) {
                    console.error('Failed to copy: ', err);
                }
            }
        )
    """)
}
