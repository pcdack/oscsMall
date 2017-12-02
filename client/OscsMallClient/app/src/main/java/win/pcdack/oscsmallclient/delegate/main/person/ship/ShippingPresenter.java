package win.pcdack.oscsmallclient.delegate.main.person.ship;

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
 * Created by pcdack on 17-11-23.
 *
 */

class ShippingPresenter implements ShippingContract.Presenter{
    private ShippingContract.View iView;
    private int pageNum=1;
    private Disposable myDisposable;
    ShippingPresenter(ShippingContract.View iView) {
        this.iView = iView;
    }

    @Override
    public void subscribe() {
        getShippingItems(true);
    }



    @Override
    public void unSubscribe() {
        myDisposable.dispose();
    }
    public void getShippingItems(final boolean isRefresh) {
        final boolean loadMore;
        if (isRefresh) {
            iView.showSwipeRefresh();
            pageNum= 1;
            loadMore=false;
        } else {
            pageNum+= 1;
            loadMore=true;
        }
        RxRestClient.builder().url(GlobalUrlVal.SHIP_LIST)
                .params("pageNum",pageNum)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> datas=new ShippingDataCoventer().setJsonData(msg).convert();
                        if (isRefresh) {
                            iView.onDataInit(datas);
                            iView.cancalSwipeRefresh();
                        }else {
                            iView.onDataAdd(datas);
                        }
                        if (loadMore)
                            iView.onLoadMoreDataComplete();
                    }

                    @Override
                    public void onStatusIsError(int code, String msg) {
                        iView.setErrorInfo(code, msg);
                        if (loadMore)
                            iView.onLoadMoreDataFail();
                    }

                    @Override
                    public void onObserverFailure() {
                        iView.setWarningInfo();
                        if (loadMore)
                            iView.onLoadMoreDataFail();
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

    @Override
    public void deleteShipById(int id, final int position) {
        RxRestClient.builder().url(GlobalUrlVal.SHIP_DEL)
                .params("shippingId",id)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        iView.onDelSuccess(position);
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
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });
    }
}
