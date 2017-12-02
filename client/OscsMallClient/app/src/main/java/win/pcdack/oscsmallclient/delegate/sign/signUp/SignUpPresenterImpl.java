package win.pcdack.oscsmallclient.delegate.sign.signUp;

import android.text.TextUtils;
import android.util.Patterns;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.oscsmallclient.base.BaseObserver;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class SignUpPresenterImpl implements SignUpContract.Presenter{
    private SignUpContract.View iView;
    private Disposable disposable;
    public SignUpPresenterImpl(SignUpContract.View iView) {
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
    public boolean checkIsNotEmail(String str) {
        if (TextUtils.isEmpty(str) || !Patterns.EMAIL_ADDRESS.matcher(str).matches()){
            iView.setEmailError("邮箱格式错误");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIsNotPhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length()>11){
            iView.setPhoneError("手机号码格式错误,需要11位");
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
    public boolean checkIsNotRePassword(String rePassword, String password) {
        if (TextUtils.isEmpty(rePassword) || !rePassword.equals(password)){
            iView.setRePasswordError("重新输入密码错误");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIsNotQuestion(String question) {
        if (TextUtils.isEmpty(question)) {
            iView.setQuestionError("不能为空");
        }
        return !TextUtils.isEmpty(question);
    }

    @Override
    public boolean checkIsNotAnswer(String answer) {
        if (TextUtils.isEmpty(answer)){
            iView.setAnswerError("不能为空");
        }
        return !TextUtils.isEmpty(answer);
    }

    @Override
    public void register(String username, String email, String phone
            , String password, String question, String answer) {
        RxRestClient.builder()
                .url(GlobalUrlVal.SIGN_UP_URL)
                .params("username",username)
                .params("email",email)
                .params("phone",phone)
                .params("password",password)
                .params("question",question)
                .params("answer",answer)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        iView.onRegisterSuccess(msg);
                    }

                    @Override
                    public void onStatusIsError(int code, String msg) {
                        iView.onRegisterError(code, msg);
                    }

                    @Override
                    public void onObserverFailure() {
                        iView.onRegisterFailure();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable=d;
                        iView.setLoadingStart();
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
        if (disposable!=null)
            disposable.dispose();
    }
}
