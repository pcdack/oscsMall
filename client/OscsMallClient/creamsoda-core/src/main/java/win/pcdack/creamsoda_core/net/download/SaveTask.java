package win.pcdack.creamsoda_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;
import win.pcdack.creamsoda_core.app.CreamSoda;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.util.file.FileUtil;

/**
 * Created by pcdack on 17-10-8.
 *
 */

public class SaveTask extends AsyncTask<Object,Void,File> {
    private final ISuccess SUCESS;
    private final IRequest IREQUEST;

    public SaveTask(ISuccess SUCESS, IRequest IREQUEST) {
        this.SUCESS = SUCESS;
        this.IREQUEST = IREQUEST;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloaderDir= (String) params[0];
        String extension= (String) params[1];
        final ResponseBody responseBody= (ResponseBody) params[2];
        final String name= (String) params[3];
        final InputStream is=responseBody.byteStream();
        if (TextUtils.isEmpty(downloaderDir)) {
            downloaderDir="download";
        }
        if (TextUtils.isEmpty(extension)){
            extension="";
        }
        if (TextUtils.isEmpty(name)){
            //中间的参数代表头部的名称
            return FileUtil.writeToDisk(is,downloaderDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloaderDir,name);
        }

    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCESS!=null)
            SUCESS.onSuccess(file.getPath());
        if (IREQUEST!=null)
            IREQUEST.onRequestFinish();
        autoInstallApk(file);
    }
    //apk文件自动安装
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            CreamSoda.getApplicationContext().startActivity(install);
        }
    }
}
