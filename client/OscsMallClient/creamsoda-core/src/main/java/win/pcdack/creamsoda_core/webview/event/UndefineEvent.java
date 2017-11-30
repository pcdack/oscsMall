package win.pcdack.creamsoda_core.webview.event;

import android.util.Log;

/**
 * Created by pcdack on 17-10-24.
 *
 */

public class UndefineEvent extends Event{
    @Override
    public String execute(String params) {
        Log.e("event", "params: "+params);
        return null;
    }
}
