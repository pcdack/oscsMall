package win.pcdack.oscsmallclient.delegate.scroll_launcher;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import win.pcdack.ec.fast_dev.launcher.NormalScrollLauncherDelegate;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class ScrollLauncher extends NormalScrollLauncherDelegate{
    private ArrayList<Integer> pics=new ArrayList<>();
    private IScrollLauncherFinish iScrollLauncherFinish;

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext((Activity) context);
    }
    /*
    * Deprecated on API 23
    * Use onAttachToContext instead
    */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    private void onAttachToContext(Activity activity) {
        if (activity instanceof IScrollLauncherFinish) {
            iScrollLauncherFinish= (IScrollLauncherFinish) activity;
        }
    }


    @Override
    public ArrayList<Integer> pics() {
        pics.add(R.mipmap.launcher01);
        pics.add(R.mipmap.launcher02);
        pics.add(R.mipmap.launcher03);
        pics.add(R.mipmap.launcher04);
        pics.add(R.mipmap.launcher05);
        return pics;
    }
    @Override
    public void onSubItemClick(int position) {
        if (position==pics.size()-1)
            if (iScrollLauncherFinish!=null) {
                Logger.d("点击成功");
                iScrollLauncherFinish.onScrollLauncherFinish();
            }
    }

}
