package win.pcdack.creamsoda_core.ui.camera;

import android.net.Uri;

/**
 * Created by pcdack on 17-11-1.
 *
 */

public final class CameraImageBean {
    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
