package win.pcdack.oscsmallclient;

import android.app.Application;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;

import cn.jpush.android.api.JPushInterface;
import es.dmoral.toasty.Toasty;
import okhttp3.logging.HttpLoggingInterceptor;
import win.pcdack.creamsoda_core.app.CreamSoda;
import win.pcdack.oscsmallclient.database.DatabaseManager;
import win.pcdack.oscsmallclient.delegate.main.discover.SharedEvent;
import win.pcdack.oscsmallclient.delegate.main.discover.ToastyEvent;
import win.pcdack.oscsmallclient.icon.CategoryModule;

/**
 * Created by pcdack on 17-11-8.
 *
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("creamsoda","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        CreamSoda.init(this)
                .withIconifyIcon(new CategoryModule())
                .withIconifyIcon(new FontAwesomeModule())
                .withApiHost("http://127.0.0.1")
                .withLoggerAdapter(new AndroidLogAdapter())
                .withJarCookie(cookieJar)
                .withInterceptor(loggingInterceptor)
                .withAndroidUtilCode(true)                  //App的某些功能依赖于AndroidUtilCode，所以尽量打开
                .withTimeOut(1000)
                .withJsInterface("creamsoda")
                .withWebEvent("share",new SharedEvent())
                .withWebEvent("comment",new ToastyEvent())
                .withWebEvent("zan",new ToastyEvent())
                .config();
        DatabaseManager.getInstance().initDao(this);
        Toasty.Config.getInstance()
                .setErrorColor(ContextCompat.getColor(this,R.color.md_red_600))
                .setWarningColor(ContextCompat.getColor(this,R.color.md_yellow_600))
                .setSuccessColor(ContextCompat.getColor(this,R.color.md_green_600))
                .setInfoColor(ContextCompat.getColor(this,R.color.md_blue_600))
                .setTextColor(ContextCompat.getColor(this,R.color.md_white_1000))
                .apply();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        MobSDK.init(this, "22c2cda972a8a", "cf0fe13a4cfa31f9fd017519640319c4");

    }
}
