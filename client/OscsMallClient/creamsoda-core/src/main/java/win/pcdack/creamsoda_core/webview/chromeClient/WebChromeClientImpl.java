package win.pcdack.creamsoda_core.webview.chromeClient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by pcdack on 17-10-24.
 *
 */

public class WebChromeClientImpl extends WebChromeClient{
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

}
