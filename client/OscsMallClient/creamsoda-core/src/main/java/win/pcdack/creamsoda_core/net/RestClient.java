package win.pcdack.creamsoda_core.net;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.net.callback.RequestCallBacks;
import win.pcdack.creamsoda_core.net.download.DownloadHandler;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.loader.LoadType;

/**
 * Created by pcdack on 17-10-6.
 *
 */

public class RestClient {
    private final String URL;
    private final WeakHashMap<String,Object> PARAMS;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURA;
    private final IRequest IREQUEST;
    private final RequestBody REEQUESTBODY;
    private final LoadType LOAD_TYPE;
    private final Context CONTEXT;
    private final File FILE;

    //download
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FILE_NAME;

    public RestClient(String URL,
                      WeakHashMap<String, Object> PARAMS,
                      ISuccess ISUCCESS,
                      IError IERROR,
                      IFailure IFAILURA,
                      IRequest IREQUEST,
                      File FILE,
                      RequestBody REEQUESTBODY,
                      LoadType LOAD_TYPE,
                      Context CONTEXT,
                      String DOWNLOAD_DIR,
                      String EXTENSION,
                      String FILE_NAME) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.ISUCCESS = ISUCCESS;
        this.IERROR = IERROR;
        this.FILE=FILE;
        this.IFAILURA = IFAILURA;
        this.IREQUEST = IREQUEST;
        this.REEQUESTBODY = REEQUESTBODY;
        this.LOAD_TYPE = LOAD_TYPE;
        this.CONTEXT = CONTEXT;
        this.DOWNLOAD_DIR=DOWNLOAD_DIR;
        this.EXTENSION=EXTENSION;
        this.FILE_NAME=FILE_NAME;
    }
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    private void request(HttpMethod httpMethod){

        final RestService restService=RestCreator.getRestService();
        Call<String> call=null;
        if (IREQUEST!=null) {
            IREQUEST.onRequestStart();
            if (LOAD_TYPE!=null){
                CreamSodaLoader.showLoader(CONTEXT,LOAD_TYPE.name());
            }
        }
        switch (httpMethod){
            case GET:
                call=restService.get(URL,PARAMS);
                break;
            case PUT:
                call=restService.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call=restService.putRaw(URL,REEQUESTBODY);
                break;
            case POST:
                call=restService.post(URL,PARAMS);
                break;
            case POST_RAW:
                call=restService.postRaw(URL,REEQUESTBODY);
                break;
            case DELETE:
                call=restService.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody=RequestBody.
                        create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body=MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call=RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;
        }
        if (call!=null){
            call.enqueue(new RequestCallBacks(ISUCCESS,IERROR,IFAILURA,IREQUEST,LOAD_TYPE));
        }

    }
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        if (REEQUESTBODY == null)
            request(HttpMethod.POST);
        else {
            if (!PARAMS.isEmpty())
                throw new RuntimeException("can not have params");
            request(HttpMethod.POST_RAW);
        }
    }
    public final void put(){
        if (REEQUESTBODY == null)
            request(HttpMethod.PUT);
        else {
            if(!PARAMS.isEmpty())
                throw new RuntimeException("can not have params");
            request(HttpMethod.PUT_RAW);
        }
    }
    public final void upload() {
        request(HttpMethod.UPLOAD);
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void download(){
        new DownloadHandler(URL,PARAMS,ISUCCESS,IERROR,IFAILURA,IREQUEST,DOWNLOAD_DIR,EXTENSION,FILE_NAME)
                .Handle();
    }
}
