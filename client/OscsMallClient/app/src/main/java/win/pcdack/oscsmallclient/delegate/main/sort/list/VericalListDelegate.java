package win.pcdack.oscsmallclient.delegate.main.sort.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.base.BaseObserver;
import win.pcdack.oscsmallclient.delegate.main.sort.SortDelegate;

/**
 * Created by pcdack on 17-11-18.
 *
 */

public class VericalListDelegate extends CreamSodaDelegate {
    @BindView(R.id.sort_vertical_list)
    RecyclerView list_recycler;

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
    private void initData(){
        RxRestClient.builder()
                .url(GlobalUrlVal.CATEGORY_LIST)
                .params("categoryId",0)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        ArrayList<MultipleItemEntity> mDatas=new VerticalListDataCoverter().setJsonData(msg).convert();
                        final SortDelegate sortDelegate=getParentDelegate();
                        final VerticalListAdapter adapter=new VerticalListAdapter(mDatas,sortDelegate);
                        list_recycler.setAdapter(adapter);
                    }

                    @Override
                    public void onStatusIsError(int code, String msg) {
                        getParentDelegate().getProxyActivity().showErrorMassage(code, msg);
                    }

                    @Override
                    public void onObserverFailure() {
                        getParentDelegate().getProxyActivity().showFailureMassage();
                    }

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        CreamSodaLoader.showLoader(getParentDelegate().getProxyActivity());
                    }

                    @Override
                    public void onComplete() {
                        CreamSodaLoader.cancelLoader();
                    }
                });
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager=new LinearLayoutManager(getContext());
        list_recycler.setLayoutManager(manager);
        list_recycler.setItemAnimator(null);
    }
}
