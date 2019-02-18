package ai.haiku.diez.haiku

import ai.haiku.diez.file.File
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import android.webkit.WebView

data class Haiku(val file: File) {
    @SuppressLint("SetJavaScriptEnabled")
    fun embedHaiku(view: ViewGroup): WebView {
        val webview = WebView(view.context)
        webview.setBackgroundColor(Color.TRANSPARENT)
        webview.isVerticalScrollBarEnabled = false
        webview.isHorizontalScrollBarEnabled = false
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(file.canonicalURL())
        view.addView(webview)
        return webview
    }
}
