package win.pcdack.creamsoda_core.ui.loader;

import android.content.Context;
import android.text.TextUtils;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;


/**
 * Created by pcdack on 17-10-7.
 * desc: 缓存
 */

public class LoaderCreator {
    private final static WeakHashMap<String,Indicator> LOAD_MAP=new WeakHashMap<>();
    //将Indicator缓存到内存中
    public static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView indicatorView=new AVLoadingIndicatorView(context);
        if(LOAD_MAP.get(type)==null){
            final Indicator indicator=getIndicator(type);
            LOAD_MAP.put(type,indicator);
        }
        indicatorView.setIndicator(LOAD_MAP.get(type));
        return indicatorView;
    }
    //通过反射机制获取Indicator
    private static Indicator getIndicator(String type){
        if (TextUtils.isEmpty(type)){
            return null;
        }
        final StringBuilder sb=new StringBuilder();
        if (!type.contains(".")){
            final String packageName=AVLoadingIndicatorView.class.getPackage().getName();
            sb.append(packageName).append(".indicators").append(".");
        }
        sb.append(type);
        try {
            final Class<?> avClass=Class.forName(sb.toString());
            return (Indicator) avClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
