package win.pcdack.creamsoda_core.net;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.ui.loader.LoadType;

/**
 * Created by pcdack on 17-10-6.
 *
 */

public class RestClientBuilder {
    private String mUrl;
    private final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private ISuccess iSuccess;
    private IError iError;
    private IFailure iFailure;
    private IRequest iRequest;
    private RequestBody requestBody;
    private LoadType loadType;
    private File file;
    private Context context;
    //download
    private String downloadDir;
    private String extension;
    private String fileName;
    RestClientBuilder(){

    }
    public final RestClientBuilder url(String url){
        this.mUrl=url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }
    public final RestClientBuilder params(String key, Object val){
        PARAMS.put(key,val);
        return this;
    }
    public final RestClientBuilder raw(String raw){
        requestBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }
    public final RestClientBuilder success(ISuccess iSuccess){
        this.iSuccess=iSuccess;
        return this;
    }
    public final RestClientBuilder error(IError iError){
        this.iError=iError;
        return this;
    }
    public final RestClientBuilder request(IRequest iRequest){
        this.iRequest=iRequest;
        return this;
    }
    public final RestClientBuilder failure(IFailure iFailure){
        this.iFailure=iFailure;
        return this;
    }
    public final RestClientBuilder loader(LoadType loadType, Context context){
        this.loadType=loadType;
        this.context=context;
        return this;
    }
    public final RestClientBuilder file(File file){
        this.file=file;
        return this;
    }
    public final RestClientBuilder file(String file){
        this.file=new File(file);
        return this;
    }
    public final RestClientBuilder loader(Context context){
        this.context=context;
        this.loadType=LoadType.SemiCircleSpinIndicator;
        return this;
    }
    public final RestClientBuilder dir(String dir){
        this.downloadDir=dir;
        return this;
    }
    public final RestClientBuilder extension(String extension){
        this.extension=extension;
        return this;
    }
    public final RestClientBuilder filename(String fileName){
        this.fileName=fileName;
        return this;
    }
    public final RestClient build(){
        return new RestClient(mUrl,
                PARAMS,iSuccess,
                iError,iFailure,
                iRequest,file,requestBody,
                loadType,context,downloadDir,
                extension,fileName);
    }

}
