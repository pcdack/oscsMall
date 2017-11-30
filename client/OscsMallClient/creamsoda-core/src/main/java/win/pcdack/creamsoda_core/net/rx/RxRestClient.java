package win.pcdack.creamsoda_core.net.rx;

import android.content.Context;

import java.io.File;
import java.net.URI;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import win.pcdack.creamsoda_core.net.HttpMethod;
import win.pcdack.creamsoda_core.net.RestCreator;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.net.download.DownloadHandler;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.loader.LoadType;

/**
 * Created by pcdack on 17-10-6.
 *
 */

public class RxRestClient {
    private final String URL;
    private final WeakHashMap<String,Object> PARAMS;
    private final RequestBody REEQUESTBODY;
    private final LoadType LOAD_TYPE;
    private final Context CONTEXT;
    private final File FILE;


    public RxRestClient(String URL,
                        WeakHashMap<String, Object> PARAMS,
                        File FILE,
                        RequestBody REEQUESTBODY,
                        LoadType LOAD_TYPE,
                        Context CONTEXT){
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.FILE=FILE;
        this.REEQUESTBODY = REEQUESTBODY;
        this.LOAD_TYPE = LOAD_TYPE;
        this.CONTEXT = CONTEXT;
    }
    public static RxRestClientBuilder builder(){
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod httpMethod){
        final RxRestService restService=RestCreator.getRxRestService();
        Observable<String> observable=null;
        if (LOAD_TYPE!=null){
            CreamSodaLoader.showLoader(CONTEXT,LOAD_TYPE.name());
        }
        switch (httpMethod){
            case GET:
                observable=restService.get(URL,PARAMS);
                break;
            case PUT:
                observable=restService.put(URL,PARAMS);
                break;
            case PUT_RAW:
                observable=restService.putRaw(URL,REEQUESTBODY);
                break;
            case POST:
                observable=restService.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable=restService.postRaw(URL,REEQUESTBODY);
                break;
            case DELETE:
                observable=restService.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody=RequestBody.
                        create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body=MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                observable=RestCreator.getRxRestService().upload(URL,body);
                break;
            default:
                break;
        }
        return observable;

    }
    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }
    public final Observable<String> post(){
        if (REEQUESTBODY == null)
           return request(HttpMethod.POST);
        else {
            if (!PARAMS.isEmpty())
                throw new RuntimeException("can not have params");
            return request(HttpMethod.POST_RAW);
        }
    }
    public final Observable<String> put(){
        if (REEQUESTBODY == null)
            return request(HttpMethod.PUT);
        else {
            if(!PARAMS.isEmpty())
                throw new RuntimeException("can not have params");
            return request(HttpMethod.PUT_RAW);
        }
    }
    public final Observable<String> delete(){
        return request(HttpMethod.DELETE);
    }
    public final Observable<ResponseBody> download(){
        return RestCreator.getRxRestService().download(URL,PARAMS);
    }
}
