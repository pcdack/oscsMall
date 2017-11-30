package win.pcdack.creamsoda_core.net.rx;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by pcdack on 17-10-6.
 *
 */

public interface RxRestService {
    @GET
    Observable<String> get(@Url String url, @QueryMap WeakHashMap<String, Object> params);
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap WeakHashMap<String, Object> params);
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody responseBody);
    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap WeakHashMap<String, Object> params);

    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody responseBody);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap WeakHashMap<String, Object> params);
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap WeakHashMap<String, Object> params);

    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);


}
