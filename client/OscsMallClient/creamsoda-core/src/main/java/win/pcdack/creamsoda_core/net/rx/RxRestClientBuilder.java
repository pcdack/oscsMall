package win.pcdack.creamsoda_core.net.rx;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import win.pcdack.creamsoda_core.net.RestClient;
import win.pcdack.creamsoda_core.net.RestCreator;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.ui.loader.LoadType;

/**
 * Created by pcdack on 17-10-6.
 *
 */

public class RxRestClientBuilder {
    private String mUrl;
    private final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private RequestBody requestBody;
    private LoadType loadType;
    private File file;
    private Context context;
    //download
//    private String downloadDir;
//    private String extension;
//    private String fileName;
    RxRestClientBuilder(){

    }
    public final RxRestClientBuilder url(String url){
        this.mUrl=url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }
    public final RxRestClientBuilder params(String key, Object val){
        PARAMS.put(key,val);
        return this;
    }
    public final RxRestClientBuilder raw(String raw){
        requestBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }
    public final RxRestClientBuilder loader(LoadType loadType, Context context){
        this.loadType=loadType;
        this.context=context;
        return this;
    }
    public final RxRestClientBuilder file(File file){
        this.file=file;
        return this;
    }
    public final RxRestClientBuilder loader(Context context){
        this.context=context;
        this.loadType=LoadType.SemiCircleSpinIndicator;
        return this;
    }
    public final RxRestClient build(){
        return new RxRestClient(mUrl,
                PARAMS,file,requestBody,
                loadType,context);
    }

}
