package win.pcdack.oscsmallclient.delegate.main.discover;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.dmoral.toasty.Toasty;
import win.pcdack.creamsoda_core.webview.event.Event;

/**
 * Created by pcdack on 17-11-18.
 *
 */

public class SharedEvent extends Event{

    @Override
    public String execute(String params) {
        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
        final String title = object.getString("title");
        final String url = object.getString("url");
        final String imageUrl = object.getString("imageUrl");
        final String text = object.getString("text");
        Toasty.info(getContext(),"分享").show();
        return null;
    }
}
