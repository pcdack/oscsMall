package win.pcdack.oscsmallclient;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.orhanobut.logger.Logger;

import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import qiu.niorgai.StatusBarCompat;
import win.pcdack.creamsoda_core.activities.ProxyActivity;
import win.pcdack.creamsoda_core.app.AccentManager;
import win.pcdack.creamsoda_core.app.IUserChecker;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.status.AppStatus;
import win.pcdack.creamsoda_core.util.storage.CreamSodaPreference;
import win.pcdack.oscsmallclient.delegate.launcher.ILauncherFinish;
import win.pcdack.oscsmallclient.delegate.launcher.Launcher;
import win.pcdack.oscsmallclient.delegate.main.EcBottomDelegate;
import win.pcdack.oscsmallclient.delegate.main.index.IndexDelegate;
import win.pcdack.oscsmallclient.delegate.scroll_launcher.IScrollLauncherFinish;
import win.pcdack.oscsmallclient.delegate.scroll_launcher.ScrollLauncher;
import win.pcdack.oscsmallclient.delegate.sign.signIn.ISignInStatusListener;
import win.pcdack.oscsmallclient.delegate.sign.signIn.SignInDelegate;
import win.pcdack.oscsmallclient.delegate.sign.signUp.ISignUpStatusListener;
import win.pcdack.oscsmallclient.delegate.sign.signUp.SignUpDelegate;

public class MainActivity extends ProxyActivity implements ILauncherFinish ,IScrollLauncherFinish,ISignInStatusListener,ISignUpStatusListener{
    //delegate调度室

    @Override
    public CreamSodaDelegate setRootDelegate() {
        return new Launcher();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null) {
            actionBar.hide();
        }
        StatusBarCompat.translucentStatusBar(this,true);
    }



    @Override
    public void showErrorMassage(int errorCode, String errorMsg) {
        Toasty.error(this,"错误码:"+errorCode+"\t错误信息:"+errorMsg).show();
    }
    @Override
    public void showErrorMassage(String errorMsg) {
        Toasty.error(this,"\t错误信息:"+errorMsg).show();
    }

    @Override
    public void showFailureMassage() {
        Toasty.warning(this,"无网络").show();
    }
    @Override
    public void showFailureMassage(String msg) {
        Toasty.warning(this,msg).show();
    }

    @Override
    public void showSuccessMassage(String info) {
        Toasty.success(this,info).show();
    }

    @Override
    public void showInfoMassage(@NonNull String info) {
        Toasty.info(this,info).show();
    }

    @Override
    public void onLauncherFinish() {
        if (CreamSodaPreference.getAppFlag(AppStatus.HAS_FIRST_LAUNCHER_APP.name())){
            AccentManager.checkAccent(new IUserChecker() {
                @Override
                public void userSignIn() {
                    getSupportDelegate().start(new EcBottomDelegate());
                }

                @Override
                public void userNotSignIn() {
                    getSupportDelegate().start(new SignInDelegate());
                }
            });
        }else {
            getSupportDelegate().start(new ScrollLauncher());
        }
    }
    @Override
    public void onScrollLauncherFinish() {
        CreamSodaPreference.setAppFlag(AppStatus.HAS_FIRST_LAUNCHER_APP.name(), true);
        AccentManager.checkAccent(new IUserChecker() {
            @Override
            public void userSignIn() {
                getSupportDelegate().start(new EcBottomDelegate());
            }

            @Override
            public void userNotSignIn() {
                getSupportDelegate().start(new SignInDelegate());
            }
        });
    }


    @Override
    public void goToSignUp() {
        Logger.d("gotoSignUp");
        getSupportDelegate().start(new SignUpDelegate());
    }

    @Override
    public void loginSuccess() {
        AccentManager.setSignStatus(true);
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void goToSignIn() {
        getSupportDelegate().start(new SignInDelegate());
    }

    @Override
    public void registerSuccess() {
        getSupportDelegate().startWithPop(new SignInDelegate());
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
