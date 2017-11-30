package win.pcdack.oscsmallclient.delegate.main.discover;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.dmoral.toasty.Toasty;
import win.pcdack.creamsoda_core.webview.event.Event;

/**
 * Created by pcdack on 17-11-18.
 *
 */

public class ToastyEvent extends Event {
    @Override
    public String execute(String params) {
        JSONObject json= JSON.parseObject(params);
        String info=json.getString("action");
        Toasty.info(getContext(),info).show();
        return null;
    }
}
