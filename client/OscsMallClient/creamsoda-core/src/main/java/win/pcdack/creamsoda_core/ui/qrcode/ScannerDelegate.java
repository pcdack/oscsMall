package win.pcdack.creamsoda_core.ui.qrcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;


import butterknife.BindView;
import butterknife.OnClick;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import win.pcdack.creamsoda_core.R;
import win.pcdack.creamsoda_core.R2;
import win.pcdack.creamsoda_core.delegates.BaseDelegate;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;
import win.pcdack.creamsoda_core.util.callback.CallBackType;
import win.pcdack.creamsoda_core.util.callback.IGlobalCallback;

/**
 * Created by pcdack on 17-11-2.
 *
 */

public class ScannerDelegate extends BaseDelegate implements ZBarScannerView.ResultHandler {
    @OnClick(R2.id.back_icon)
    void onBackClick(){
        this.getSupportDelegate().pop();
    }
    @BindView(R2.id.scan_view)
    ScanView scanView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_scan_qrcode;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        scanView.setAutoFocus(true);
        scanView.setResultHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (scanView != null)
            scanView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (scanView != null) {
            scanView.stopCameraPreview();
            scanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked")
        final IGlobalCallback<String> callback = CallBackManager.getInstance()
                .getCallback(CallBackType.ON_SCAN);
        callback.executeCallback(result.getContents());
    }

}
