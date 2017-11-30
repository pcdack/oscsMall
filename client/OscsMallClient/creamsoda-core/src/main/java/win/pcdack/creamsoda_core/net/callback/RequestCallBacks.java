package win.pcdack.creamsoda_core.net.callback;


import android.os.Handler;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.loader.LoadType;

/**
 * Created by pcdack on 17-10-7.
 *
 */

public class RequestCallBacks implements Callback<String>{
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURA;
    private final IRequest IREQUEST;
    private final LoadType LOADTYPE;
    private static final Handler HANDLER= new Handler();

    public RequestCallBacks(ISuccess ISUCCESS,
                            IError IERROR,
                            IFailure IFAILURA,
                            IRequest IREQUEST,
                            LoadType LOAD_TYPE) {
        this.ISUCCESS = ISUCCESS;
        this.IERROR = IERROR;
        this.IFAILURA = IFAILURA;
        this.IREQUEST = IREQUEST;
        this.LOADTYPE=LOAD_TYPE;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onResponse(Call<String> call, @NonNull Response<String> response) {
        if (response.isSuccessful() && call.isExecuted()){
            if (ISUCCESS!=null)
                ISUCCESS.onSuccess(response.body());

        }else if (IERROR!=null)
            IERROR.onError(response.code(),response.message());
        if (IREQUEST!=null)
            IREQUEST.onRequestFinish();

        cancalLoader();
    }

    private void cancalLoader() {
        if (LOADTYPE!=null)
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CreamSodaLoader.cancelLoader();
                }
            },1000);

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (IFAILURA!=null)
            IFAILURA.onFailure();
        if (IREQUEST!=null)
            IREQUEST.onRequestFinish();
        cancalLoader();
    }

}
