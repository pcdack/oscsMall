package win.pcdack.creamsoda_core.ui.qrcode;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;
import win.pcdack.creamsoda_core.R;

/**
 * Created by pcdack on 17-11-2.
 *
 */

public class CreamSodaViewFinderView extends ViewFinderView{
    public CreamSodaViewFinderView(Context context) {
        super(context);
    }

    public CreamSodaViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder=true;
        mBorderPaint.setColor(ContextCompat.getColor(context, R.color.md_red_600));
        mLaserPaint.setColor(ContextCompat.getColor(context, R.color.md_red_600));
    }
}
