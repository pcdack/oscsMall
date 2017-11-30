package win.pcdack.creamsoda_core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.R;
import win.pcdack.creamsoda_core.app.ConfigType;
import win.pcdack.creamsoda_core.app.CreamSoda;
import win.pcdack.creamsoda_core.util.dimen.DimenUtil;

/**
 * Created by pcdack on 17-10-7.
 *
 */

public class CreamSodaLoader {
    private static final int LOADER_SIZE_SCALE=8;
    private static final int HEIGHT_OFFSET_SIZE=10;
    private static final ArrayList<AppCompatDialog> DIALOGS=new ArrayList<>();
    private static final String DEFAULT_LOADER="BallPulseIndicator";
    public static void showLoader(Context context){
        showLoader(context,DEFAULT_LOADER);
    }

    public static void showLoader(Context context,String type){
        final AppCompatDialog dialog=new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView indicatorView= LoaderCreator.create(type,context);
        dialog.setContentView(indicatorView);
        int screenWidth;
        int screenHeight;
        if (CreamSoda.getConfiguration(ConfigType.UTILS)) {
            screenWidth = ScreenUtils.getScreenWidth();
            screenHeight = ScreenUtils.getScreenHeight();
        }else {
            screenWidth = DimenUtil.getScreenWidth();
            screenHeight =DimenUtil.getScreenHeight();
        }
        final Window dialogWindow=dialog.getWindow();
        if (dialogWindow!=null){
            final WindowManager.LayoutParams params=dialogWindow.getAttributes();
            params.width=screenWidth/LOADER_SIZE_SCALE;
            params.height=screenHeight/LOADER_SIZE_SCALE;
            params.height=params.height+screenHeight/HEIGHT_OFFSET_SIZE;
            params.gravity= Gravity.CENTER;
        }
        DIALOGS.add(dialog);
        dialog.show();
    }
    public static void cancelLoader(){
        for (AppCompatDialog dialog : DIALOGS) {
            if (dialog!=null && dialog.isShowing())
                dialog.cancel();
        }
    }


}
