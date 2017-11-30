package win.pcdack.creamsoda_core.webview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import win.pcdack.creamsoda_core.webview.chromeClient.WebChromeClientImpl;
import win.pcdack.creamsoda_core.webview.client.WebClientImpl;
import win.pcdack.creamsoda_core.webview.route.RouteKey;
import win.pcdack.creamsoda_core.webview.route.Router;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class WebViewDelegateImpl extends WebViewDelegate{


    public static WebViewDelegateImpl create(String url){
        final Bundle bundle=new Bundle();
        bundle.putString(RouteKey.URL.name(),url);
        final WebViewDelegateImpl delegate=new WebViewDelegateImpl();
        delegate.setArguments(bundle);
        return delegate;
    }


    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (getUrl()!=null) {
            Router.getRouter().loadPage(this,getUrl());
        }
    }



    @Override
    public IWebViewInitiallizer setInitiallizer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebClientImpl webClient=new WebClientImpl(this);
        return webClient;
    }

    @Override
    public WebChromeClient initChromeClient() {
        return new WebChromeClientImpl();
    }
}
