package win.pcdack.oscsmallclient.delegate.main.person;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.database.DatabaseManager;
import win.pcdack.oscsmallclient.database.UserProfile;
import win.pcdack.oscsmallclient.database.UserProfileDao;

/**
 * Created by pcdack on 17-11-22.
 *
 */

public class PersonPresenter implements PersonContract.Presenter{
    private PersonContract.View iView;
    private Disposable disposable;
    public PersonPresenter(PersonContract.View iView) {
        this.iView = iView;
    }
    @Override
    public void subscribe() {
        Observable.create(new ObservableOnSubscribe<UserProfile>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<UserProfile> e) throws Exception {
                UserProfileDao mDao=DatabaseManager.getInstance().getDao();
                List<UserProfile> userProfiles=mDao.loadAll();
                if (userProfiles.size()==0){
                    e.onError(new Throwable("need login"));
                }else {
                    UserProfile userProfile = userProfiles.get(userProfiles.size() - 1);
                    e.onNext(userProfile);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserProfile>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable=d;
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onNext(@NonNull UserProfile userProfile) {
                        if (userProfile==null){
                            iView.setErrorInfo(10,"需要登录");
                        }else {
                            iView.setUserName(userProfile.getUserName());
                            iView.setImageViewBase64(userProfile.getAvatar());
                        }
                        iView.setLoadingFinish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        iView.setErrorInfo(10,"需要登录");
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });

    }

    @Override
    public void unSubscribe() {
        if (disposable!=null)
            disposable.dispose();
    }
}
