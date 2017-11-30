package win.pcdack.creamsoda_core.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;

import win.pcdack.creamsoda_core.R;
import win.pcdack.creamsoda_core.delegates.PermissionCheckerDelegate;
import win.pcdack.creamsoda_core.util.file.FileUtil;

/**
 * Created by pcdack on 17-11-1.
 *
 */

public class CameraHandler implements View.OnClickListener {
    private final AlertDialog DIALOG;
    private final PermissionCheckerDelegate DELEGRATE;

    public CameraHandler(PermissionCheckerDelegate delegate) {
        this.DELEGRATE = delegate;
        DIALOG=new AlertDialog.Builder(delegate.getContext()).create();

    }

    final void beginCameraDialog() {

        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_botom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
//            params.height = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.height=WindowManager.LayoutParams.MATCH_PARENT;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
            window.findViewById(R.id.photo_dialog_take_btn).setOnClickListener(this);
            window.findViewById(R.id.photo_dialog_cancel_btn).setOnClickListener(this);
            window.findViewById(R.id.photo_dialog_local_btn).setOnClickListener(this);
        }
    }
    private String getPhotoName(){
        return FileUtil.getFileNameByTime("IMG","jpg");
    }
    private void takePhoto(){
        final String currentPhotoName=getPhotoName();
        final Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile=new File(FileUtil.CAMERA_PHOTO_DIR,currentPhotoName);
        //兼容7.0的写法
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = DELEGRATE.getContext().getContentResolver().
                    insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //需要讲Uri路径转化为实际路径
            final File realFile =
                    FileUtils.getFileByPath(FileUtil.getRealFilePath(DELEGRATE.getContext(), uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        DELEGRATE.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);

    }
    private void pickPhoto(){
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        DELEGRATE.startActivityForResult
                (Intent.createChooser(intent, "选择获取图片的方式")
                        , RequestCodes.PICK_PHOTO);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.photo_dialog_cancel_btn) {
            DIALOG.cancel();
        } else if (i == R.id.photo_dialog_local_btn) {
            pickPhoto();
            DIALOG.cancel();
        } else if (i == R.id.photo_dialog_take_btn) {
            takePhoto();
            DIALOG.cancel();
        }
    }
}
