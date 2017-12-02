package win.pcdack.oscsmallclient.delegate.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.base.BaseObserver;


/**
 * Created by pcdack on 17-11-19.
 * 步骤：1.进行一次网络数据请求
 * 2.填充外部数据
 * 3.填充RecyclerView的数据
 * 4.数据交互
 */

class CartPresenter implements CartContract.Presenter {
    private CartContract.View iView;
    private ArrayList<MultipleItemEntity> entities;
    private ArrayList<Disposable> disposables;
    private boolean isSelectAll=false;

    CartPresenter(CartContract.View iView) {
        this.iView = iView;
        disposables=new ArrayList<>();
    }

    @Override
    public void subscribe() {
        RxRestClient.builder()
                .url(GlobalUrlVal.CART_LIST)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        //外层数据解析
                        outerLayoutDataAnalysis(msg);
                        //内层数据丢给DataCoventer
                        recyclerDataAnalysis(msg);
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

    private void recyclerDataAnalysis(String msg) {
        entities=new CartDataCoventer().setJsonData(msg).convert();
        iView.setCartItems(entities);
    }
    private void recyclerDataUpdate(String msg) {
        entities=new CartDataCoventer().setJsonData(msg).convert();
        iView.updateCartItems(entities);
    }

    private void outerLayoutDataAnalysis(String msg) {
        JSONObject jsonObject= JSON.parseObject(msg);
        boolean allSelect=jsonObject.getBoolean("allChecked");
        int allPrice=jsonObject.getInteger("allPrices");
        isSelectAll=allSelect;
        iView.setAllPrices(allPrice);
        iView.setAllSelectStatus(allSelect);
    }

    @Override
    public void unSubscribe() {
        if (disposables!=null && disposables.size()>0) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
        }
    }
    @Override
    public void deleteItems() {
        StringBuilder sb=new StringBuilder();
        for (MultipleItemEntity entity : entities) {
            boolean isChecked=entity.getField(MultipleFields.CHECKED);
            if (isChecked) {
                int id=entity.getField(MultipleFields.ID);
                sb.append(id).append(",");
            }
        }
        String ids=sb.toString();
        RxRestClient.builder()
                .url(GlobalUrlVal.CART_DELETE)
                .params("productIds",ids)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        outerLayoutDataAnalysis(msg);
                        recyclerDataUpdate(msg);
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
    public boolean isAllSelect() {
        return isSelectAll;
    }
    @Override
    public void recyclerItemAddOnClick(int position) {
        MultipleItemEntity entity=entities.get(position);
        int quantity=entity.getField(MultipleFields.QUANTITY);
        int productId=entity.getField(MultipleFields.ID);
        quantity++;
        updateQuantity(quantity,productId);
    }

    @Override
    public void recyclerItemLesOnClick(int position) {
        MultipleItemEntity entity=entities.get(position);
        int quantity=entity.getField(MultipleFields.QUANTITY);
        int productId=entity.getField(MultipleFields.ID);
        quantity--;
        updateQuantity(quantity,productId);
    }
    @Override
    public void recyclerItemSelectIconClick(int position) {
        MultipleItemEntity entity=entities.get(position);
        int productId=entity.getField(MultipleFields.ID);
        boolean isSelect=entity.getField(MultipleFields.CHECKED);
        if (isSelect)
            updateChecked(GlobalUrlVal.CART_UN_SELECT,productId);
        else
            updateChecked(GlobalUrlVal.CART_SELECT,productId);
    }
    private void updateChecked(String url,int productId) {
        RxRestClient.builder()
                .url(url)
                .params("productId",productId)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        outerLayoutDataAnalysis(msg);
                        recyclerDataUpdate(msg);

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

    //外层
    @Override
    public void recyclerItemSelectAll() {
        RxRestClient.builder()
                .url(GlobalUrlVal.CART_SELECT_ALL)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        outerLayoutDataAnalysis(msg);
                        recyclerDataUpdate(msg);
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
    public void recyclerItemUnSelectAll() {
        RxRestClient.builder()
                .url(GlobalUrlVal.CART_UN_SELECT_ALL)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        outerLayoutDataAnalysis(msg);
                        recyclerDataUpdate(msg);
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

    private void updateQuantity(int quantity,int productId) {
        RxRestClient.builder()
                .url(GlobalUrlVal.CART_UPDATE)
                .params("count",quantity)
                .params("productId",productId)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        outerLayoutDataAnalysis(msg);
                        recyclerDataUpdate(msg);
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
}
