package win.pcdack.oscsmallclient.delegate.search.search_goods;

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
 * Created by pcdack on 17-11-26.
 *
 */

public class SearchGoodsPresenter implements SearchGoodsContract.Presenter{
    private SearchGoodsContract.View view;
    private ArrayList<Disposable> disposables;

    public SearchGoodsPresenter(SearchGoodsContract.View view) {
        this.view = view;
        disposables=new ArrayList<>();
    }

    @Override
    public void initSearchByKeyWord(String keyword) {
        RxRestClient.builder().url(GlobalUrlVal.PRODUCT_SEARCH_BY_KEYWORDS)
                .params("keyword",keyword)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> entities=new SearchDataCovnter().setJsonData(msg).convert();
                        view.setData(entities);
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
    public void initSearchByKeyWordAndCategory(String keyword, int categoryId) {
        RxRestClient.builder().url(GlobalUrlVal.PRODUCT_SEARCH)
                .params("keyword",keyword)
                .params("category",categoryId)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> entities=new SearchDataCovnter().setJsonData(msg).convert();
                        view.setData(entities);
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
        if (disposables!=null && disposables.size()>0){
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
        }
    }
}
