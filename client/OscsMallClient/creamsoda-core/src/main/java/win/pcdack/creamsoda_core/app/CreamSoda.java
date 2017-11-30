package win.pcdack.creamsoda_core.app;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by pcdack on 17-11-8.
 * desc:        CreamSoda的核心
 * function:    用来初始化应用配置
 */

public class CreamSoda {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT,context);
        return Configurator.getInstance();
    }
    public static HashMap<ConfigType,Object> getConfigurations(){
        return Configurator.getInstance().getConfigurations();
    }
    public static Context getApplicationContext(){
        return Configurator.getInstance().getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }
    public static <T>T getConfiguration(@NonNull ConfigType key){
        return Configurator.getInstance().getConfiguration(key);
    }



}
