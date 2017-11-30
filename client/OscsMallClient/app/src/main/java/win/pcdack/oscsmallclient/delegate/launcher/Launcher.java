package win.pcdack.oscsmallclient.delegate.launcher;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

import win.pcdack.ec.fast_dev.type.TextType;
import win.pcdack.ec.fast_dev.launcher.NormalLauncherDelegate;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class Launcher extends NormalLauncherDelegate {
    private ILauncherFinish iLauncherFinish;
    @Override
    public Object iconTextOrResource() {
        return new TextType.Builder()
                .text("{fa-shopping-bag}")
                .color(Color.RED)
                .textSize(60)
                .build();
    }
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
        if (activity instanceof ILauncherFinish) {
            iLauncherFinish= (ILauncherFinish) activity;
        }
    }

    @Override
    public TextType sloganText() {
        Typeface fontType= Typeface
                .createFromAsset(getProxyActivity().getAssets(),"fonts/YYG.TTF");
        return new TextType.Builder()
                .typeface(fontType)
                .text("新购物，新生活")
                .textSize(15)
                .build();
    }


    @Override
    public TextType timerText() {
        return new TextType.Builder().textSize(15).color(Color.WHITE).build();
    }

    @Override
    public void onTimeFinish() {
        if (iLauncherFinish!=null) {
            iLauncherFinish.onLauncherFinish();
        }
    }

    @Override
    public int delaySeconds() {
        return 5;
    }



}
