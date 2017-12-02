package win.pcdack.oscsmallclient.delegate.sign.signIn;

import android.text.TextUtils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.oscsmallclient.delegate.sign.SignHandler;


/**
 * Created by pcdack on 17-11-11.
 *
 */

public class SignInPresenterImpl implements SignInContract.Presenter {
    private SignInContract.View iView;

    public SignInPresenterImpl(SignInContract.View iView) {
        this.iView = iView;
    }

    @Override
    public boolean checkIsNotUserName(String str) {
        if (TextUtils.isEmpty(str)){
            iView.setNameError("不能为空");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIsNotPassword(String password) {
        if (TextUtils.isEmpty(password) || password.length()<8){
            iView.setPasswordError("密码格式错误,需要8位");
            return false;
        }
        return true;
    }

    @Override
    public void login(String username, String password) {
        RxRestClient.builder()
                .url(GlobalUrlVal.SIGN_IN_URL)
                .params("username",username)
                .params("password",password)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        JSONObject object= JSON.parseObject(s);
                        int code=object.getInteger("status");
                        String msg=object.getString("msg");
                        if (code==0) {
                            SignHandler.signIn(s);
                            iView.setLoginSuccess(msg);
                        }else {
                            iView.setLoginError(code,msg);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        iView.setLoginFailure();
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
