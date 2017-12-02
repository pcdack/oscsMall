package win.pcdack.oscsmallclient.delegate.main.index;



import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.base.BaseObserver;

/**
 * Created by pcdack on 17-11-15.
 *
 */

@SuppressWarnings("WeakerAccess")
public class IndexPresenter implements IndexContract.Presenter{
    private IndexContract.View iView;
    private Disposable myDisposable;
    private int pageNum=1;
    public IndexPresenter(IndexContract.View iView) {
        this.iView = iView;
    }

    @Override
    public void subscribe() {
        getIndexItems(true);
    }

    @Override
    public void unSubscribe() {
        myDisposable.dispose();
    }


    @Override
    public void getIndexItems(final boolean isRefresh) {
        if (isRefresh) {
            iView.showSwipeRefresh();
            pageNum= 1;
        } else {
            pageNum+= 1;
        }
        RxRestClient.builder()
                .url(GlobalUrlVal.PRODUCT_LIST_URL)
                .params("pageNum",pageNum)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> datas=new IndexDataCoventer().setJsonData(msg).convert();
                        if (isRefresh) {
                            iView.onDataInit(datas);
                            iView.cancalSwipeRefresh();
                        }else {
                            iView.onDataAdd(datas);
                        }
                    }

                    @Override
                    public void onStatusIsError(int code, String msg) {
                        iView.setErrorInfo(code, msg);
                    }

                    @Override
                    public void onObserverFailure() {
                        iView.setWarningInfo();
                    }



                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        myDisposable=d;
                        if (!isRefresh)
                            iView.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        if (!isRefresh)
                            iView.setLoadingFinish();
                    }
                });

    }
}
