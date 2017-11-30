package win.pcdack.oscsmallclient.delegate.main.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import win.pcdack.creamsoda_core.delegates.bottom.BottomItemDelegate;
import win.pcdack.creamsoda_core.ui.camera.RequestCodes;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.BaseDecoration;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;
import win.pcdack.creamsoda_core.util.callback.IGlobalCallback;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.detail.goods.GoodsDetailDelegate;
import win.pcdack.oscsmallclient.delegate.search.SearchDelegate;

/**
 * Created by pcdack on 17-11-15.
 *
 */

public class IndexDelegate extends BottomItemDelegate
        implements IndexContract.View,SwipeRefreshLayout.OnRefreshListener{
    private IndexContract.Presenter iPresenter;
    private MultipleRecyclerAdapter adapter;
    private boolean isInit=true;
    @BindView(R.id.index_qrcode_scan)
    IconTextView indexQrcodeScan=null;
    @BindView(R.id.index_search_bar)
    AppCompatEditText indexSearchBar=null;
    @OnFocusChange(R.id.index_search_bar)
    void OnSeachFacusChange(boolean hasFocus){
        if (hasFocus)
            getParentDelegate().getSupportDelegate().start(new SearchDelegate());
    }
    @BindView(R.id.index_info_message)
    IconTextView indexInfoMessage=null;
    @BindView(R.id.index_toolbar)
    Toolbar indexToolbar=null;
    @BindView(R.id.index_rv)
    RecyclerView indexRv=null;
    @BindView(R.id.index_swipe_refresh)
    SwipeRefreshLayout indexSwipeRefresh=null;
    @OnClick(R.id.index_qrcode_scan)
    void qrCodeClicked(){
        startScanWithCheck(this.getParentDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefresh();
        initRecyclerView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        iPresenter=new IndexPresenter(this);
        iPresenter.getIndexItems(true);
        CallBackManager.getInstance().addCallback(RequestCodes.BANNER, new IGlobalCallback<Integer>() {
            @Override
            public void executeCallback(@Nullable Integer args) {
                //noinspection ConstantConditions
                startProductDetailDelegate(args);
            }
        });
        CallBackManager.getInstance().addCallback(RequestCodes.SCAN, new IGlobalCallback<String>() {
            @Override
            public void executeCallback(@Nullable String args) {
                getParentDelegate().getProxyActivity().showInfoMassage("扫描成功信息为:"+args);
            }
        });
    }

    @Override
    public void setLoadingStart() {
        CreamSodaLoader.showLoader(getParentDelegate().getProxyActivity());
    }

    @Override
    public void setLoadingFinish() {
        CreamSodaLoader.cancelLoader();
    }

    @Override
    public void setErrorInfo(int code, String info) {
        getParentDelegate().getProxyActivity().showErrorMassage(code, info);
    }

    @Override
    public void setWarningInfo() {
        getParentDelegate().getProxyActivity().showFailureMassage();
    }

    @Override
    public void cancalSwipeRefresh() {
        indexSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void showSwipeRefresh() {
        indexSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void onDataInit(ArrayList<MultipleItemEntity> datas) {
        if (isInit) {
            adapter = MultipleRecyclerAdapter.create(datas);
            indexRv.setAdapter(adapter);
            isInit=false;
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    MultipleItemEntity entity= (MultipleItemEntity) adapter.getData().get(position);
                    int productId=entity.getField(MultipleFields.ID);
                    startProductDetailDelegate(productId);
                }
            });
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    iPresenter.getIndexItems(false);
                }
            },indexRv);
        }else
            adapter.setNewData(datas);
    }

    private void startProductDetailDelegate(int productId) {
        getParentDelegate().getSupportDelegate().start(GoodsDetailDelegate.create(productId));
    }

    @Override
    public void onDataAdd(ArrayList<MultipleItemEntity> datas) {
        adapter.addData(datas);
    }

    @Override
    public void onRefresh() {
        iPresenter.getIndexItems(true);
    }
    private void initRefresh(){
        indexSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_light
        );
        indexSwipeRefresh.setProgressViewOffset(true,90,300);
        indexSwipeRefresh.setOnRefreshListener(this);
    }
    private void initRecyclerView(){
        final GridLayoutManager manager=new GridLayoutManager(getContext(),4);
        indexRv.setLayoutManager(manager);
        indexRv.addItemDecoration(new BaseDecoration(ContextCompat.getColor(getContext(),R.color.gray),3));
    }
}
