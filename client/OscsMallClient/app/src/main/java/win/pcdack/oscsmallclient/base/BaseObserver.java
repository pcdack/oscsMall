package win.pcdack.oscsmallclient.base;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public abstract class BaseObserver implements Observer<String>{
    public abstract void onStatusIsSuccess(String msg);
    public abstract void onStatusIsError(int code,String msg);
    public abstract void onObserverFailure();

    @Override
    public void onNext(@NonNull String s) {
        JSONObject object=JSON.parseObject(s);
        int status=object.getInteger("status");
        String msg=object.getString("msg");
        String data=object.getString("data");
        if (status==0){
            if (TextUtils.isEmpty(msg)){
                onStatusIsSuccess(data);
            }else if (TextUtils.isEmpty(data)){
                onStatusIsSuccess(msg);
            }
        } else {
            onStatusIsError(status, msg);
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        onObserverFailure();
    }
}
