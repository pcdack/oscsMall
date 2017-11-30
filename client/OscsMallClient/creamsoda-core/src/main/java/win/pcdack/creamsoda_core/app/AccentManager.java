package win.pcdack.creamsoda_core.app;

import win.pcdack.creamsoda_core.util.storage.CreamSodaPreference;

/**
 * Created by pcdack on 17-10-14.
 *
 */

public class AccentManager {
    private enum SignTag{
        SIGN_TAG
    }
    public static void setSignStatus(boolean signInfo){
        CreamSodaPreference.setAppFlag(SignTag.SIGN_TAG.name(),signInfo);
    }
    private static boolean isSignIn(){
        return CreamSodaPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }
    public static void checkAccent(IUserChecker iUserChecker){
        if (isSignIn())
            iUserChecker.userSignIn();
        else
            iUserChecker.userNotSignIn();
    }
}
