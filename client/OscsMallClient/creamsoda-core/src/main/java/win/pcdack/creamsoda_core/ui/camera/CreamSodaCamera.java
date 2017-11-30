package win.pcdack.creamsoda_core.ui.camera;

import android.net.Uri;

import win.pcdack.creamsoda_core.delegates.PermissionCheckerDelegate;
import win.pcdack.creamsoda_core.util.file.FileUtil;

/**
 * Created by pcdack on 17-11-1.
 *
 */

public class CreamSodaCamera {
    public static Uri createCropFile(){
        return Uri.parse
                (FileUtil.createFile("crop_image"
                        , FileUtil.getFileNameByTime("IMG", "jpg"))
                        .getPath());
    }
    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
