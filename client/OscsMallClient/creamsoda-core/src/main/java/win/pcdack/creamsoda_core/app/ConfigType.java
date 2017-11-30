package win.pcdack.creamsoda_core.app;

/**
 * Created by pcdack on 17-11-8.
 *
 */

public enum ConfigType {
    API_HOST,               //Base Url
    APPLICATION_CONTEXT,    //整个程序的上下文
    CONFIG_READY,           //初始化完成
    TIME_OUT,               //超时时间
    ICON,                   //Iconify
    LOGGER_ADAPTER,         //采用自定义LoggerAdapter
    INTERCEPTOR,            //Okhttp拦截器
    JAVA_COOKIE,            //JarCookie
    UTILS,                  //是否启用Utils工具包
    JS_INTERFACE
}
