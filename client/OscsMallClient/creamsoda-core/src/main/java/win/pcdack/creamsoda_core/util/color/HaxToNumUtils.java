package win.pcdack.creamsoda_core.util.color;

import android.support.annotation.ColorInt;

/**
 * Created by pcdack on 17-10-22.
 *
 */

public class HaxToNumUtils {
    public static int getRed(@ColorInt int color){
        return getColor(color,2,4);
    }
    public static int getGreen(@ColorInt int color){
        return getColor(color,4,6);
    }
    public static int getBlue(@ColorInt int color){
        return getColor(color,6,8);
    }
    private static int getColor(@ColorInt int color,int beginIndex,int endIndex){
        String hex=Integer.toHexString(color);
        String currentColorValue=hex.substring(beginIndex,endIndex);
        return Integer.parseInt(currentColorValue,16);
    }

}
