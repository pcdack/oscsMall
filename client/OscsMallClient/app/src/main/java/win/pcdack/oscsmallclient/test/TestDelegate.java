package win.pcdack.oscsmallclient.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.banner.NormalBannerHolderCreator;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-11-10.
 * 测试Delegate
 */

public class TestDelegate extends CreamSodaDelegate implements TestContract.View{
    private ArrayList<Integer> PICS=new ArrayList<>();
    @BindView(R.id.my_convenient_banner)
    ConvenientBanner myConvenientBanner;
    @BindView(R.id.my_index_rv)
    RecyclerView recyclerView;
    private TestAdapter adapter;
    private TestContract.Presenter presenter;
    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        PICS.add(R.mipmap.test1);
        PICS.add(R.mipmap.test2);
        initRecycleView();
        presenter=new TestPresenter(this);
        presenter.subscribe();
        if (PICS.size()>0) {
            myConvenientBanner.setPages(new NormalBannerHolderCreator(), PICS).
                    setPageIndicator(new int[]{win.pcdack.ec.fast_ec.R.drawable.dot_normal, win.pcdack.ec.fast_ec.R.drawable.dot_focus}).
                    setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).
                    startTurning(3000).
                    setPageTransformer(new DefaultTransformer()).
                    setCanLoop(true);
        }
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setLoadingStart() {
        CreamSodaLoader.showLoader(getProxyActivity());
    }

    @Override
    public void setLoadingFinish() {
        CreamSodaLoader.cancelLoader();
    }

    @Override
    public void setErrorInfo(int code, String info) {
        getProxyActivity().showErrorMassage(code, info);
    }

    @Override
    public void setWarningInfo() {
        getProxyActivity().showFailureMassage();
    }

    @Override
    public void setData(ArrayList<MultipleItemEntity> entities) {
        adapter=new TestAdapter(entities);
        recyclerView.setAdapter(adapter);
    }


// // TODO: 17-11-10 RxGet测试
//    private void rxGetTest2() {
//        RxRestClient.builder()
//                .url("https://www.baidu.com")
//                .build()
//                .get()
//                 .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull String s) {
//                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//
//    private void rxGetTest() {
//        final WeakHashMap<String,Object> params=new WeakHashMap<>();
//        final Observable<String> observable= RestCreator.getRxRestService()
//                .get("https://www.baidu.com",params);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull String s) {
//                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
}
