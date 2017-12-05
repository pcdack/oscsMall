package win.pcdack.oscsmallclient.delegate.sign.signIn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class SignInDelegate extends CreamSodaDelegate implements SignInContract.View {
    @BindView(R.id.edit_sign_in_name)
    EditText nameEdit;
    @BindView(R.id.edit_sign_in_password)
    EditText passwordEdit;
    @BindView(R.id.sign_up_tv)
    TextView forgotPassword;
    private SignInPresenterImpl iPresenter;
    private ISignInStatusListener listener;


    @Override
    public Object setLayout() {
        return R.layout.sign_in_nice_delegate;
    }

    @OnClick(R.id.sign_up_tv)
    void onSignInToSignUp() {
        if (listener != null)
            listener.goToSignUp();
    }

    @OnFocusChange(R.id.edit_sign_in_name)
    void nameOnFocus(boolean hasFoucs) {
        if (!hasFoucs)
            iPresenter.checkIsNotUserName(getInputEditText(nameEdit));
    }

    @OnFocusChange(R.id.edit_sign_in_password)
    void passwordOnFocus(boolean hasFoucs) {
        if (!hasFoucs)
            iPresenter.checkIsNotPassword(getInputEditText(passwordEdit));
    }

    @OnClick(R.id.sign_in_btn)
    void onSignIn() {
        boolean isPass = true;
        if (!iPresenter.checkIsNotUserName(getInputEditText(nameEdit))) {
            isPass = false;
        }
        if (!iPresenter.checkIsNotPassword(getInputEditText(passwordEdit))) {
            isPass = false;
        }
        if (isPass) {
            iPresenter.login(getInputEditText(nameEdit), getInputEditText(passwordEdit));
        }

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        iPresenter = new SignInPresenterImpl(this);
    }

    @Override
    public void setNameError(String nameError) {
        nameEdit.setError(nameError);
    }

    @Override
    public void setPasswordError(String passwordError) {
        passwordEdit.setError(passwordError);
    }

    @Override
    public void setLoginSuccess(String info) {
        getProxyActivity().showSuccessMassage(info);
        listener.loginSuccess();
    }

    @Override
    public void setLoginError(int code, String msg) {
        getProxyActivity().showErrorMassage(code, msg);
    }

    @Override
    public void setLoginFailure() {
        getProxyActivity().showFailureMassage();
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
        getProxyActivity().showErrorMassage(code, info);
    }

    @Override
    public void setWarningInfo() {
        getProxyActivity().showFailureMassage();
    }

    private String getInputEditText(EditText editText) {
        return editText.getText().toString().trim();
    }


    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext((Activity) context);
    }

    private void onAttachToContext(Activity activity) {
        if (activity instanceof ISignInStatusListener) {
            listener = (ISignInStatusListener) activity;
        }
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




    @OnClick(R.id.sign_in_btn)
    public void onViewClicked() {
    }
}
