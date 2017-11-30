package win.pcdack.oscsmallclient.delegate.main.sort.content;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import win.pcdack.GlobalUrlVal;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.net.rx.RxRestClient;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.GlobalVal;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.base.BaseObserver;
import win.pcdack.oscsmallclient.delegate.search.search_goods.SearchGoodsDelegate;

/**
 * Created by pcdack on 17-11-18.
 *
 */

public class ContentDelegate extends CreamSodaDelegate {
    @BindView(R.id.sort_content)
    RecyclerView recyclerView;

    private final static String ARG_CONTENT_ID="CONTENT_ID";
    private int mInitId=-1;

    public static ContentDelegate newInsance(int contentId){
        final Bundle bundle=new Bundle();
        bundle.putInt(ARG_CONTENT_ID,contentId);
        final ContentDelegate contentDelegate=new ContentDelegate();
        contentDelegate.setArguments(bundle);
        return contentDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args=getArguments();
        if (args!=null){
            mInitId=args.getInt(ARG_CONTENT_ID);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecyclerView();
    }

    private void initData() {
        if (mInitId==1)
            mInitId = GlobalVal.DEFAULT_CATEGORY;
        RxRestClient.builder()
                .url(GlobalUrlVal.CATEGORY_LIST)
                .params("categoryId",mInitId)
                .build()
                .post()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onStatusIsSuccess(String msg) {
                        final ArrayList<MultipleItemEntity> datas=new ContentDataConverter().setJsonData(msg).convert();
                        ContentAdapter adapter=new ContentAdapter(datas);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                String keyword=datas.get(position).getField(MultipleFields.NAME);
                                int categoryId=datas.get(position).getField(MultipleFields.ID);
                                getParentDelegate().getParentDelegate().getSupportDelegate().start(SearchGoodsDelegate.create(keyword,categoryId));
                            }
                        });
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
        final StaggeredGridLayoutManager manager=new
                StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }
}
