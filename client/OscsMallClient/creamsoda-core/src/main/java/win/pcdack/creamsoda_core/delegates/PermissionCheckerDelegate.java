package win.pcdack.creamsoda_core.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import me.yokeyword.fragmentation.SupportFragment;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import win.pcdack.creamsoda_core.ui.camera.CameraImageBean;
import win.pcdack.creamsoda_core.ui.camera.CreamSodaCamera;
import win.pcdack.creamsoda_core.ui.camera.RequestCodes;
import win.pcdack.creamsoda_core.ui.qrcode.ScannerDelegate;
import win.pcdack.creamsoda_core.ui.recycler.BaseDecoration;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;
import win.pcdack.creamsoda_core.util.callback.CallBackType;
import win.pcdack.creamsoda_core.util.callback.IGlobalCallback;

/**
 * Created by pcdack on 17-11-10.
 * func:动态权限申请
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {
    UCrop.Options options=new UCrop.Options();
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera(){
        CreamSodaCamera.start(this);
    }
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseDelegate baseDelegate){
        baseDelegate.getSupportDelegate().startForResult(new ScannerDelegate(),RequestCodes.SCAN);
    }
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void getCameraDenied(){
        getProxyActivity().showFailureMassage("给权限，老铁");
    }
    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRational(PermissionRequest request){
        showRationaleDialog(request);
    }
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        getProxyActivity().showFailureMassage("永久拒绝权限");
    }
    //调用Camera
    public void startCameraWithCheck(){
//        getProxyActivity().showFailureMassage("给权限，老铁");
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }
    public void startScanWithCheck(BaseDelegate delegate){
        PermissionCheckerDelegatePermissionsDispatcher.startScanWithPermissionCheck(this,delegate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    initUCrop();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .withOptions(options)
                            .start(getContext(), this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        initUCrop();
                        final String pickCropPath = CreamSodaCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withOptions(options)
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback = CallBackManager
                            .getInstance()
                            .getCallback(CallBackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    getProxyActivity().showFailureMassage("出错");
                    break;
                default:
                    break;
            }
        }
    }
    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }
    private void initUCrop(){
//        options.setToolbarColor(Color.argb(256,256,44,44));
        options.setCompressionQuality(50);
    }
}
