package win.pcdack.creamsoda_core.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by pcdack on 17-10-24.
 *
 */

public class WebViewInitializer {
    @SuppressLint("SetJavaScriptEnabled")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public WebView createWebView(WebView webView){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //可以截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化settings
        final WebSettings settings=webView.getSettings();
        final String ua=settings.getUserAgentString();
        settings.setUserAgentString(ua+"creamsoda");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        //缓存
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webView;
    }
}
