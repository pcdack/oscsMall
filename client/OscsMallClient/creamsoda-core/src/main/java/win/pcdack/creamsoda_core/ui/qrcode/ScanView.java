package win.pcdack.creamsoda_core.ui.qrcode;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by pcdack on 17-11-2.
 *
 */

public class ScanView extends ZBarScannerView{
    public ScanView(Context context) {
        this(context,null);
    }

    public ScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new CreamSodaViewFinderView(context);
    }
}
