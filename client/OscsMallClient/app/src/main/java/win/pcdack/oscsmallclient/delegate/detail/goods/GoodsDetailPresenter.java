package win.pcdack.oscsmallclient.delegate.detail.goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.oscsmallclient.base.BaseObserver;

/**
 * Created by pcdack on 17-11-26.
 *
 */

class GoodsDetailPresenter implements GoodsDetailContract.Presenter{
    private GoodsDetailContract.View iView;
    private int mGoodsId;
    private int cartNum=0;
    private ArrayList<Disposable> disposable;

    GoodsDetailPresenter(GoodsDetailContract.View iView) {
        disposable=new ArrayList<>();
        this.iView = iView;
    }

    @Override
    public void initData(final int productId) {
        RxRestClient.builder()
                .url(GlobalUrlVal.PRODUCT_DATAIL)
                .params("productId",productId)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        JSONObject object=JSON.parseObject(msg);
                        mGoodsId=object.getInteger("id");
                        iView.initRichText(object.getString("detail"));
                        ArrayList<String> imageUrls=new ArrayList<>();
                        String mainImgUrl=object.getString("main_image");
                        imageUrls.add(mainImgUrl);
                        String subImgUrls=object.getString("sub_images");
                        String[] subs=subImgUrls.split(",");
                        Collections.addAll(imageUrls, subs);
                        iView.initBanner(imageUrls);
                        GoodsInfoBean bean=new GoodsInfoBean();
                        bean.setName(object.getString("name"));
                        bean.setPrice(object.getInteger("price"));
                        bean.setStock(object.getInteger("stock"));
                        bean.setStatus(object.getInteger("status"));
                        iView.initGoodsInfo(bean);

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
                        disposable.add(d);
                        iView.setLoadingStart();
                    }

                    @Override
                    public void onComplete() {
                        iView.setLoadingFinish();
                    }
                });
    }

    @Override
    public void addIntoCart() {
        if (mGoodsId==0)
            iView.setErrorInfo(100,"程序出错，嘤嘤嘤");
        RxRestClient.builder()
                .url(GlobalUrlVal.CART_ADD)
                .params("productId",mGoodsId)
                .params("count",++cartNum)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        iView.setCircleTextViewNum(cartNum);
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
                        disposable.add(d);
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
        if (disposable!=null){
            for (Disposable disposable1 : disposable) {
                disposable1.dispose();
            }
        }
    }
}
