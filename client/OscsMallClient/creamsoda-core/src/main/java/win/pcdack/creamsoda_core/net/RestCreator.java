package win.pcdack.creamsoda_core.net;



import android.util.Log;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import win.pcdack.creamsoda_core.app.ConfigType;
import win.pcdack.creamsoda_core.app.CreamSoda;
import win.pcdack.creamsoda_core.net.rx.RxRestService;

/**
 * Created by pcdack on 17-10-6.
 *
 */

public class RestCreator {
    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    }
    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }


    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }
    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.RX_REST_SERVICE;
    }
//    public static
    private static final class RetrofitHolder{
        private static final String BASE_URL= (String) CreamSoda.getConfigurations().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT_CLIENT=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    private static final Retrofit RX_RETROFIT_CLIENT=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkhttpHolder.OK_HTTP_CLIENT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    }
    private static final class OkhttpHolder{
        private static final int TIME_OUT= (CreamSoda.getConfigurations().get(ConfigType.TIME_OUT)==null)?60: (int) CreamSoda.
                getConfigurations().get(ConfigType.TIME_OUT);
        private static final CookieJar COOKIE_JAR=CreamSoda.getConfiguration(ConfigType.JAVA_COOKIE);
        private static final ArrayList<Interceptor> INTERCEPTORS=CreamSoda.getConfiguration(ConfigType.INTERCEPTOR);
        private static OkHttpClient.Builder BUILDER=new OkHttpClient.Builder();
        private static OkHttpClient.Builder addInterceptorAndCookie(){
            if (INTERCEPTORS!=null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            if (COOKIE_JAR!=null){
                BUILDER.cookieJar(COOKIE_JAR);
                Log.i("cookie", "addCookieJar: ");
            }
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT=
                addInterceptorAndCookie()
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE= RetrofitHolder
                .RETROFIT_CLIENT.create(RestService.class);
    }
    private static final class RxRestServiceHolder{
        private static final RxRestService RX_REST_SERVICE= RetrofitHolder
                .RX_RETROFIT_CLIENT.create(RxRestService.class);
    }

}
