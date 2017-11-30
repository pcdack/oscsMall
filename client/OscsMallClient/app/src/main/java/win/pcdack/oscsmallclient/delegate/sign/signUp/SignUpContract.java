package win.pcdack.oscsmallclient.delegate.sign.signUp;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class SignUpContract {
    interface View extends BaseView{
        void setNameError(String errorMsg);
        void setEmailError(String errorMsg);
        void setPhoneError(String errorMsg);
        void setPasswordError(String errorMsg);
        void setRePasswordError(String errorMsg);
        void setQuestionError(String errorMsg);
        void setAnswerError(String errorMsg);
        void onRegisterSuccess(String msg);
        void onRegisterError(int code,String msg);
        void onRegisterFailure();

    }
    interface Presenter extends BasePresenter{
        boolean checkIsNotUserName(String str);
        boolean checkIsNotEmail(String str);
        boolean checkIsNotPhone(String phone);
        boolean checkIsNotPassword(String password);
        boolean checkIsNotRePassword(String rePassword,String password);
        boolean checkIsNotQuestion(String question);
        boolean checkIsNotAnswer(String answer);
        void register(String username, String email,
                      String phone, String password, String question,
                      String answer);
    }
}
