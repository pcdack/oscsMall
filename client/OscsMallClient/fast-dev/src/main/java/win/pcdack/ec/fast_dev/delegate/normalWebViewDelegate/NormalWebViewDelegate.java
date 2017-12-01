package win.pcdack.ec.fast_dev.delegate.normalWebViewDelegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.ec.fast_ec.R;
import win.pcdack.ec.fast_ec.R2;

/**
 * Created by pcdack on 17-12-1.
 *
 */

public class NormalWebViewDelegate extends CreamSodaDelegate {

    private static final String NORMAL_TITLE="normal_title";
    private static final String NORMAL_URL="normal_url";
    private String title;
    private String url;
    @BindView(R2.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R2.id.web_view)
    WebView webView;
    public static NormalWebViewDelegate create(String title,String url) {
        final Bundle bundle = new Bundle();
        bundle.putString(NORMAL_TITLE, title);
        bundle.putString(NORMAL_URL,url);
        final NormalWebViewDelegate goodsDetailDelegate = new NormalWebViewDelegate();
        goodsDetailDelegate.setArguments(bundle);
        return goodsDetailDelegate;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle myBundle = this.getArguments();
        if (myBundle!=null){
            title=myBundle.getString(NORMAL_TITLE);
            url=myBundle.getString(NORMAL_URL);
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_webview;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if (!TextUtils.isEmpty(title))
            toolbarTitle.setText(title);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);//设置容许使用JavaScript
        webView.getSettings().setSupportZoom(false);//容许缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebViewClient());
        CreamSodaLoader.showLoader(getProxyActivity());
        webView.loadUrl(url);
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public class WebViewClient extends WebChromeClient {
        //监听进度条的变化

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100) {
                CreamSodaLoader.cancelLoader();
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @OnClick(R2.id.back_icon)
    public void onViewClicked() {
        getSupportDelegate().pop();
    }
}
