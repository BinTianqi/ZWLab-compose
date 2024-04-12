import androidx.compose.material3.ColorScheme

actual fun getPlatform() = "web"

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

actual fun getDynamicTheme(): Pair<ColorScheme, ColorScheme>? = null