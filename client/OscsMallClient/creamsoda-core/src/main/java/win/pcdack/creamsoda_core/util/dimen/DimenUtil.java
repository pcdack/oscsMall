package win.pcdack.creamsoda_core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import win.pcdack.creamsoda_core.app.CreamSoda;

/**
 * Created by pcdack on 17-10-7.
 *
 */

public class DimenUtil {
    /**
     * 得到屏幕的宽
     */
    public static int getScreenWidth(){
        final Resources resources= CreamSoda.getApplicationContext().getResources();
        final DisplayMetrics metrics=resources.getDisplayMetrics();
        return metrics.widthPixels;
    }
    public static int getScreenHeight(){
        final Resources resources= CreamSoda.getApplicationContext().getResources();
        final DisplayMetrics metrics=resources.getDisplayMetrics();
        return metrics.heightPixels;
    }
}
