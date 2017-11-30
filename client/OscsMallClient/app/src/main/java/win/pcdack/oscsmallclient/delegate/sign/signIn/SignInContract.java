package win.pcdack.oscsmallclient.delegate.sign.signIn;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class SignInContract {
    interface View extends BaseView{
        void setNameError(String nameError);
        void setPasswordError(String passwordError);
        void setLoginSuccess(String response);
        void setLoginError(int code,String msg);
        void setLoginFailure();
    }
    interface Presenter extends BasePresenter{
        boolean checkIsNotUserName(String str);
        boolean checkIsNotPassword(String password);
        void login(String username,String password);
    }
}
