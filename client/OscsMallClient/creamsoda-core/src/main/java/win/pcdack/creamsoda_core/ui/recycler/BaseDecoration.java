package win.pcdack.creamsoda_core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by pcdack on 17-10-22.
 *
 */

public class BaseDecoration extends DividerItemDecoration{
    public BaseDecoration(@ColorInt int color,int width){
        setDividerLookup(new BaseLookupImpl(color,width));
    }
    public static BaseDecoration create(@ColorInt int color,int width){
        return new BaseDecoration(color,width);
    }

}
