package win.pcdack.creamsoda_core.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public interface IWebViewInitiallizer {
    WebView initWebView(WebView webView);

    WebViewClient initWebViewClient();

    WebChromeClient initChromeClient();
}
