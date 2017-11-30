package win.pcdack.creamsoda_core.webview.event;

import android.content.Context;
import android.webkit.WebView;

import win.pcdack.creamsoda_core.webview.WebViewDelegate;

/**
 * Created by pcdack on 17-10-24.
 *
 */

public abstract class Event implements IEvent {
    private Context context;
    private String action;
    private WebViewDelegate delegrate;
    private String mUrl;
    private WebView webView;

    public WebView getWebView(){
        return this.delegrate.getWebView();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public WebViewDelegate getDelegrate() {
        return delegrate;
    }

    public void setDelegrate(WebViewDelegate delegrate) {
        this.delegrate = delegrate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
