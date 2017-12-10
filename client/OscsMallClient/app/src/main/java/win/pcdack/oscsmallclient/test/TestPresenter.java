package win.pcdack.oscsmallclient.test;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.base.BaseObserver;

/**
 * Created by pcdack on 17-12-8.
 *
 */

public class TestPresenter implements TestContract.Presenter{
    private TestContract.View view;
    private ArrayList<Disposable> disposables;

    public TestPresenter(TestContract.View view) {
        this.view = view;
        disposables=new ArrayList<>();
    }

    @Override
    public void subscribe() {
        RxRestClient.builder()
                .url("http://172.28.84.29:8080/index/index.do")
                .build()
                .get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> multipleItemEntities=new TestDataCovnter().setJsonData(msg).convert();
                        view.setData(multipleItemEntities);
                    }

                    @Override
                    public void onStatusIsError(int code, String msg) {
                        view.setErrorInfo(code, msg);
                    }

                    @Override
                    public void onObserverFailure() {
                        view.setWarningInfo();
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                        view.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        view.setLoadingFinish();
                    }
                });

    }

    @Override
    public void unSubscribe() {
        if (disposables.size()>0) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
        }
    }

}
