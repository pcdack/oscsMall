package win.pcdack.creamsoda_core.webview.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.webview.WebViewDelegate;
import win.pcdack.creamsoda_core.webview.WebViewDelegateImpl;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class Router {
    private Router() {
    }
    private static class Holder{
        private static final Router INSCANCE=new Router();
    }
    public static Router getRouter(){
        return Holder.INSCANCE;
    }
    public final boolean handlWebUrl(WebViewDelegate delegate,String url){
        if (url.contains("tel:")){
            callPhone(delegate.getContext(),url);
            return true;
        }
        final CreamSodaDelegate topDelegate=delegate.getTopDelegrate();
        final WebViewDelegateImpl webViewDelegate=WebViewDelegateImpl.create(url);
            topDelegate.getSupportDelegate().start(webViewDelegate);
        return true;
    }
    private void callPhone(Context context,String uri){
        final Intent intent=new Intent(Intent.ACTION_CALL);
        final Uri data=Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context,intent,null);
    }
    private void loadWebPage(WebView webView,String url){
        if (webView!=null){
            webView.loadUrl(url);
        }else {
            throw new NullPointerException("WebView is NULL");
        }
    }
    private void loadLocalPage(WebView webView,String url){
        loadWebPage(webView,"file:///android_asset/"+url);
    }
    public void loadPage(WebView webView,String url){
        if (URLUtil.isNetworkUrl(url)|| URLUtil.isAssetUrl(url)){
            loadWebPage(webView, url);
        }else {
            loadLocalPage(webView, url);
        }
    }
    public final void loadPage(WebViewDelegate delegate,String url){
        loadPage(delegate.getWebView(),url);
    }
}
