package win.pcdack.oscsmallclient.delegate.sign.signUp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;


import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-11-11.
 *
 */
@SuppressWarnings("unused")
public class SignUpDelegate extends CreamSodaDelegate implements SignUpContract.View{
    private SignUpContract.Presenter iPresenter;
    private ISignUpStatusListener listener;
    @BindView(R.id.edit_sign_up_name)
    TextInputEditText nameEdit;
    @OnFocusChange(R.id.edit_sign_up_name)
    void onUserNameFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotUserName(getInputEditText(nameEdit));
    }
    @BindView(R.id.edit_sign_up_email)
    TextInputEditText emailEdit;
    @OnFocusChange(R.id.edit_sign_up_email)
    void onEmailFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotEmail(getInputEditText(emailEdit));
    }
    @BindView(R.id.edit_sign_up_phone)
    TextInputEditText phoneEdit;
    @OnFocusChange(R.id.edit_sign_up_phone)
    void onPhoneFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotPhone(getInputEditText(phoneEdit));
    }
    @BindView(R.id.edit_sign_up_question)
    TextInputEditText questionEdit;
    @OnFocusChange(R.id.edit_sign_up_question)
    void onQuestionFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotQuestion(getInputEditText(questionEdit));
    }
    @BindView(R.id.edit_sign_up_answer)
    TextInputEditText answerEdit;
    @OnFocusChange(R.id.edit_sign_up_answer)
    void onAnswerFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotAnswer(getInputEditText(answerEdit));
    }
    @BindView(R.id.edit_sign_up_pssword)
    TextInputEditText passwordEdit;
    @OnFocusChange(R.id.edit_sign_up_pssword)
    void onPasswordFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotPassword(getInputEditText(passwordEdit));
    }
    @BindView(R.id.edit_sign_up_repassword)
    TextInputEditText rePasswordEdit;
    @OnFocusChange(R.id.edit_sign_up_repassword)
    void onRepasswordFocusChange(boolean hasFoucs){
        if (!hasFoucs)
            iPresenter.checkIsNotRePassword(getInputEditText(rePasswordEdit)
                    ,getInputEditText(passwordEdit));
    }
    @OnClick(R.id.sign_up_btn)
    void onSignUpBtnClick(){
        boolean isPass=true;
        if (!iPresenter.checkIsNotUserName(getInputEditText(nameEdit)))
            isPass=false;
        if (!iPresenter.checkIsNotEmail(getInputEditText(emailEdit)))
            isPass=false;
        if (!iPresenter.checkIsNotPhone(getInputEditText(phoneEdit)))
            isPass=false;
        if (!iPresenter.checkIsNotQuestion(getInputEditText(questionEdit)))
            isPass=false;
        if (!iPresenter.checkIsNotAnswer(getInputEditText(answerEdit)))
            isPass=false;
        if (!iPresenter.checkIsNotPassword(getInputEditText(passwordEdit)))
            isPass=false;
        if (!iPresenter.checkIsNotRePassword(getInputEditText(rePasswordEdit),getInputEditText(passwordEdit)))
            isPass=false;
        if (isPass) {
            iPresenter.register(getInputEditText(nameEdit),getInputEditText(emailEdit),
                    getInputEditText(phoneEdit),getInputEditText(passwordEdit),
                    getInputEditText(questionEdit),getInputEditText(answerEdit));
        }
    }
    @OnClick(R.id.sign_in_tv)
    void onSignInTvClick(){
        if (listener!=null)
            listener.goToSignIn();
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        iPresenter=new SignUpPresenterImpl(this);
    }

    @Override
    public void setLoadingStart() {
        CreamSodaLoader.showLoader(getProxyActivity());
    }

    @Override
    public void setLoadingFinish() {
        CreamSodaLoader.cancelLoader();
    }

    @Override
    public void setErrorInfo(int code, String info) {
        getProxyActivity().showErrorMassage(code,info);
    }

    @Override
    public void setWarningInfo() {
        getProxyActivity().showFailureMassage();
    }

    @Override
    public void setNameError(String errorMsg) {
        nameEdit.setError(errorMsg);
    }

    @Override
    public void setEmailError(String errorMsg) {
        emailEdit.setError(errorMsg);
    }

    @Override
    public void setPhoneError(String errorMsg) {
        phoneEdit.setError(errorMsg);
    }

    @Override
    public void setPasswordError(String errorMsg) {
        passwordEdit.setError(errorMsg);
    }

    @Override
    public void setRePasswordError(String errorMsg) {
        rePasswordEdit.setError(errorMsg);
    }

    @Override
    public void setQuestionError(String errorMsg) {
        questionEdit.setError(errorMsg);
    }

    @Override
    public void setAnswerError(String errorMsg) {
        answerEdit.setError(errorMsg);
    }

    @Override
    public void onRegisterSuccess(String msg) {
        getProxyActivity().showSuccessMassage(msg);
        listener.registerSuccess();
    }

    @Override
    public void onRegisterError(int code, String msg) {
        getProxyActivity().showErrorMassage(code, msg);
    }

    @Override
    public void onRegisterFailure() {
        getProxyActivity().showFailureMassage();
    }
    private String getInputEditText(TextInputEditText editText){
        return editText.getText().toString().trim();
    }
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext((Activity) context);
    }

    private void onAttachToContext(Activity activity) {
        if (activity instanceof ISignUpStatusListener){
            listener= (ISignUpStatusListener) activity;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.unSubscribe();
    }

    /*
         * Deprecated on API 23
         * Use onAttachToContext instead
         */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }
}
