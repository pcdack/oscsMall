package win.pcdack.oscsmallclient.delegate.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.GlobalUrlVal;
import win.pcdack.creamsoda_core.net.RestClient;
import win.pcdack.creamsoda_core.net.callback.IRequest;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.base.BaseObserver;
import win.pcdack.oscsmallclient.delegate.main.cart.CartDataCoventer;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShippingDataCoventer;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShippingFields;

/**
 * Created by pcdack on 17-11-27.
 *
 */

class OrderCreatePresenter implements OrderCreateContract.Presenter{
    private OrderCreateContract.View iView;
    private ArrayList<Disposable> disposables;

    OrderCreatePresenter(OrderCreateContract.View iView) {
        this.iView = iView;
        disposables=new ArrayList<>();
    }

    @Override
    public void updateShipInfo(int shipId) {
        RestClient.builder().url(GlobalUrlVal.SHIP_SELECT).params("shippingId",shipId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject object=JSON.parseObject(response);
                        int status=object.getInteger("status");
                        String msg=object.getString("msg");
                        if (status==0){
                            JSONObject data=object.getJSONObject("data");
                            final int id=data.getIntValue("id");
                            final String receiverName=data.getString("receiverName");
                            final String receiverPhone=data.getString("receiverPhone");
                            final String receiverProvince=data.getString("receiverProvince");
                            final String receiverCity=data.getString("receiverCity");
                            final String receiverDistrict=data.getString("receiverDistrict");
                            final String receiverAddress=data.getString("receiverAddress");
                            final String address=receiverProvince+receiverCity+receiverDistrict+receiverAddress;
                            MultipleItemEntity entity=MultipleItemEntity.builder()
                                    .setField(ShippingFields.ID,id)
                                    .setField(ShippingFields.MOBLIE,receiverPhone)
                                    .setField(ShippingFields.ADDRESS,address)
                                    .setField(ShippingFields.NAME,receiverName)
                                    .build();
                            iView.setShipDetail(entity);
                        }else {
                            iView.setErrorInfo(50,msg);
                        }
                    }
                })
                .request(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onRequestFinish() {
                        iView.setLoadingFinish();
                    }
                }).build().post();
    }

    @Override
    public void getShipDetail() {
        RxRestClient.builder().url(GlobalUrlVal.SHIP_LIST).build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> entities=new ShippingDataCoventer().setJsonData(msg).convert();
                        iView.setShipDetail(entities.get(0));
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
                        disposables.add(d);
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });
    }

    @Override
    public void getCartDetail() {
        int pageNum = 1;
        RxRestClient.builder().url(GlobalUrlVal.CART_LIST).params("pageNum", pageNum).build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        int price= JSON.parseObject(msg).getInteger("allPrices");
                        ArrayList<MultipleItemEntity> entities=new CartDataCoventer().setJsonData(msg).convert();
                        iView.setCartDetail(entities);
                        iView.setAllPrices(price);
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
                        disposables.add(d);
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });
    }

    @Override
    public void createOrder(int shippingId) {
        RxRestClient.builder().url(GlobalUrlVal.ORDER_CREATE).build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> entities=new ShippingDataCoventer().setJsonData(msg).convert();
                        iView.setShipDetail(entities.get(0));
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
                        disposables.add(d);
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });
    }

    @Override
    public void unSubscribe() {
        if (disposables!=null && disposables.size()>0) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
        }
    }
}
