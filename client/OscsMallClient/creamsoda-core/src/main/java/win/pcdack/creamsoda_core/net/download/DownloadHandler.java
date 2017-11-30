package win.pcdack.creamsoda_core.net.download;

import android.os.AsyncTask;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import win.pcdack.creamsoda_core.net.RestCreator;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;

/**
 * Created by pcdack on 17-10-8.
 *
 */

public class DownloadHandler {
    private final String URL;
    private final WeakHashMap<String,Object> PARAMS;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURA;
    private final IRequest IREQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public DownloadHandler(String URL, WeakHashMap<String, Object> PARAMS,
                           ISuccess ISUCCESS, IError IERROR,
                           IFailure IFAILURA, IRequest IREQUEST,
                           String DOWNLOAD_DIR, String EXTENSION,
                           String NAME) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.ISUCCESS = ISUCCESS;
        this.IERROR = IERROR;
        this.IFAILURA = IFAILURA;
        this.IREQUEST = IREQUEST;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME=NAME;
    }

    public final void Handle(){

        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody body=response.body();
                            final SaveTask task=new SaveTask(ISUCCESS,IREQUEST);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,body,NAME);

                            if (task.isCancelled() && IREQUEST!=null){
                                IREQUEST.onRequestFinish();
                            }

                        }else {
                            if (IERROR!=null){
                                IERROR.onError(response.code(),response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (IFAILURA!=null){
                            IFAILURA.onFailure();
                        }
                        RestCreator.getParams().clear();
                    }
                });
    }
}
