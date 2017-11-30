package win.pcdack.creamsoda_core.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import win.pcdack.creamsoda_core.webview.event.Event;
import win.pcdack.creamsoda_core.webview.event.EventManager;

/**
 * Created by pcdack on 17-11-8.
 * func: 使用建造者模式来完成配置
 */

@SuppressWarnings({"WeakerAccess","unused"})
public class Configurator {
    private final HashMap<ConfigType,Object> CREAM_SODA_CONFIG=new HashMap<>();
    private final ArrayList<IconFontDescriptor> ICONS=new ArrayList<>();
    private final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();
    private Configurator(){
        CREAM_SODA_CONFIG.put(ConfigType.CONFIG_READY,false);
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    final HashMap<ConfigType,Object> getConfigurations(){
        return CREAM_SODA_CONFIG;
    }
    private static final class Holder{
        final static Configurator INSTANCE=new Configurator();
    }

    public final void config(){
        initIcons();
        initAndroidUtil();
        initLogger();
        initInterceptor();
        CREAM_SODA_CONFIG.put(ConfigType.CONFIG_READY,true);
    }
    public final Configurator withApiHost(String apiHost){
        CREAM_SODA_CONFIG.put(ConfigType.API_HOST,apiHost);
        return this;
    }

    public final Configurator withAndroidUtilCode(boolean use){
        CREAM_SODA_CONFIG.put(ConfigType.UTILS,use);
        return this;
    }
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        return this;
    }
    public final Configurator withIconifyIcon(IconFontDescriptor icon){
        ICONS.add(icon);
        return this;
    }
    public final Configurator withJsInterface(@NonNull String action){
        CREAM_SODA_CONFIG.put(ConfigType.JS_INTERFACE,action);
        return this;
    }
    public final Configurator withLoggerAdapter(LogAdapter adapter){
        CREAM_SODA_CONFIG.put(ConfigType.LOGGER_ADAPTER,adapter);
        return this;
    }
    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }
    public final Configurator withIconifyIcons(List<IconFontDescriptor> icon){
        ICONS.addAll(icon);
        return this;
    }
    public final Configurator withJarCookie(CookieJar cookie){
        CREAM_SODA_CONFIG.put(ConfigType.JAVA_COOKIE,cookie);
        return this;
    }
    public final Configurator withTimeOut(int time){
        CREAM_SODA_CONFIG.put(ConfigType.TIME_OUT,time);
        return this;
    }
    @SuppressWarnings("unchecked")
    public <T> T getConfiguration(ConfigType key){
        checkConfigurations();
        return (T) CREAM_SODA_CONFIG.get(key);
    }

    private void initIcons(){
        if (ICONS.size()>0) {
            final Iconify.IconifyInitializer initializer=Iconify.with(ICONS.get(0));
            for (int i = 1 ; i <ICONS.size() ; i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }
    private void checkConfigurations(){
        final boolean isReady = (boolean) CREAM_SODA_CONFIG.get(ConfigType.CONFIG_READY);
        if (!isReady){
            throw new RuntimeException("the config is not init plase reCheck you Config");
        }
    }
    private void initAndroidUtil(){
        if (CREAM_SODA_CONFIG.containsKey(ConfigType.UTILS)) {
            boolean isUtils= (boolean) CREAM_SODA_CONFIG.get(ConfigType.UTILS);
            Context context= (Context) CREAM_SODA_CONFIG.get(ConfigType.APPLICATION_CONTEXT);
            if (isUtils && context != null)
                Utils.init((Application)context);
            else
                Logger.e("初始化AndroidUtilCode出错");

        }
    }
    private void initLogger(){
        if (CREAM_SODA_CONFIG.containsKey(ConfigType.LOGGER_ADAPTER))
            Logger.addLogAdapter((LogAdapter) CREAM_SODA_CONFIG.get(ConfigType.LOGGER_ADAPTER));
        else
            Logger.addLogAdapter(new AndroidLogAdapter());
    }
    private void initInterceptor(){
        if (INTERCEPTORS.size()>0) {
            CREAM_SODA_CONFIG.put(ConfigType.INTERCEPTOR,INTERCEPTORS);
        }
    }

}
