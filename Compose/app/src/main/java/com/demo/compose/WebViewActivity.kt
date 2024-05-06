package com.demo.compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.compose.ui.theme.ComposeTheme

/**
 * WebView in Jetpack Compose
 * from:
 * https://medium.com/@sahar.asadian90/webview-in-jetpack-compose-71f237873c2e
 *
 */
class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
//                WebViewScreen("https://weibo.com")
                WebViewClientScreen("https://weibo.com")
//                WebChromeClientScreen(activity = this, url = "https://m.weibo.com")
            }
        }
    }
}


@Composable
fun WebViewScreen(url: String) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        }
    ) { webView ->
        webView.loadUrl(url)
    }
}

@Composable
fun WebViewClientScreen(
    url: String,
    webViewClient: WebViewClient = CustomWebViewClient()
) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

class CustomWebViewClient: WebViewClient(){

    /**
     * shouldoverrideurlloading
     * will be called when the WebView changes it's URL,
     * when user clicks on the link in the WebView, or the redirect happens.
     */
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.e("COMPOSE", "request url = " + request?.url.toString())
        if(request?.url != null && request?.url.toString().contains("weibo.cn")){
            Log.e("COMPOSE", "override url to news.163.com")
            view?.loadUrl("https://news.163.com")
            return true
        }
        return false
    }

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        Log.e("COMPOSE", "request url = $url")
        if (url.contains("weibo.cn")) {
            // DO YOUR WORK
            // ...
            // ...
            return true
        }
        return false
    }
}


/**
 * WebChromeClient with file upload
 */
@Composable
fun WebChromeClientScreen(
    activity: ComponentActivity,
    url: String
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webChromeClient = customWebChromeClient()
                loadUrl(url)
            }
        }
    )
}

private fun customWebChromeClient(): WebChromeClient {
    val webChromeClient = object : WebChromeClient() {
        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            openFileChooser(filePathCallback)
            return true
        }
    }
    return webChromeClient
}

private fun openFileChooser(filePathCallback: ValueCallback<Array<Uri>>) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*"
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
}