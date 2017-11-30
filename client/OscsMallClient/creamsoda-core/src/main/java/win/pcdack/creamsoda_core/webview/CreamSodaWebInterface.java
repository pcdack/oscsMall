package win.pcdack.creamsoda_core.webview;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;

import win.pcdack.creamsoda_core.webview.event.Event;
import win.pcdack.creamsoda_core.webview.event.EventManager;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class CreamSodaWebInterface {
    private final WebViewDelegate DELEGATE;

    private CreamSodaWebInterface(WebViewDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static CreamSodaWebInterface create(WebViewDelegate delegate){
        return new CreamSodaWebInterface(delegate);
    }
    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params){
        final String action= JSON.parseObject(params).getString("action");
//        EventManager.getInsance().addEvent(action,new TestEevent());
        final Event event= EventManager.getInstance().createEvent(action);
        if (event!=null){
            event.setAction(action);
            event.setDelegrate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setmUrl(DELEGATE.getUrl());
            return event.execute(params);
        }

        return null;
    }
}
