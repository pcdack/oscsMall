package win.pcdack.creamsoda_core.webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import win.pcdack.creamsoda_core.app.ConfigType;
import win.pcdack.creamsoda_core.app.CreamSoda;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.webview.route.RouteKey;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public abstract class WebViewDelegate extends CreamSodaDelegate implements IWebViewInitiallizer {
    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebViewAvailable = false;
    private CreamSodaDelegate mTopDelegrate=null;

    public WebViewDelegate() {

    }

    public abstract IWebViewInitiallizer setInitiallizer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args=getArguments();
        mUrl=args.getString(RouteKey.URL.name());
        initWebView();
    }

    @SuppressLint("AddJavascriptInterface")
    private void initWebView() {
        if (mWebView!=null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }else {
            final IWebViewInitiallizer initiallizer=setInitiallizer();
            if (initiallizer!=null){
                final WeakReference<WebView> reference=
                        new WeakReference<>(new WebView(getContext()),WEB_VIEW_QUEUE);
                mWebView=reference.get();
                mWebView=initiallizer.initWebView(mWebView);
                mWebView.setWebViewClient(initiallizer.initWebViewClient());
                mWebView.setWebChromeClient(initiallizer.initChromeClient());
                final String action= CreamSoda.getConfiguration(ConfigType.JS_INTERFACE);
                mWebView.addJavascriptInterface(CreamSodaWebInterface.create(this),action);
                mIsWebViewAvailable=true;
            }else {
                throw new NullPointerException("Initiallizer is NULL!");
            }
        }

    }

    public WebView getWebView() {
        if (mWebView == null)
            throw new NullPointerException("WebView is NULL");
        return mIsWebViewAvailable?mWebView:null;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setmTopDelegrate(CreamSodaDelegate mTopDelegrate) {
        this.mTopDelegrate = mTopDelegrate;
    }

    public CreamSodaDelegate getTopDelegrate() {
        if (mTopDelegrate==null)
            return this;
        return mTopDelegrate;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView!=null)
            mWebView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView!=null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView=null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView!=null)
            mWebView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable=false;
    }

}
