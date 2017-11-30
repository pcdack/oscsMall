package win.pcdack.creamsoda_core.webview.client;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.loader.LoadType;
import win.pcdack.creamsoda_core.webview.IPageLoadListener;
import win.pcdack.creamsoda_core.webview.WebViewDelegate;
import win.pcdack.creamsoda_core.webview.route.Router;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class WebClientImpl extends WebViewClient{
    private final WebViewDelegate DELEGATE;
    private IPageLoadListener pageLoadListener=null;


    public WebClientImpl(WebViewDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public void setPageLoadListener(IPageLoadListener pageLoadListener) {
        this.pageLoadListener = pageLoadListener;
    }

    //低版本不支持
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }
    //低版本支持
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        return Router.getRouter().handlWebUrl(DELEGATE,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (pageLoadListener!=null)
            pageLoadListener.onLoadPageStart();
        CreamSodaLoader.showLoader(view.getContext(), LoadType.BallGridPulseIndicator.name());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (pageLoadListener!=null)
            pageLoadListener.onLoadPageEnd();
        CreamSodaLoader.cancelLoader();
    }
}
