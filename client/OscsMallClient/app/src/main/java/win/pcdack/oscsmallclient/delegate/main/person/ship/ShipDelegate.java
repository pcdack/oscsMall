package win.pcdack.oscsmallclient.delegate.main.person.ship;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;
import win.pcdack.creamsoda_core.util.callback.CallBackType;
import win.pcdack.creamsoda_core.util.callback.IGlobalCallback;
import win.pcdack.ec.fast_dev.delegate.normalDelegateWithBackAddIcon.NormalDelegateWithBackAddIcon;
import win.pcdack.oscsmallclient.GlobalVal;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.search.search_goods.SearchGoodsDelegate;

/**
 * Created by pcdack on 17-11-23.
 * func:ShipDelegate
 */

public class ShipDelegate extends NormalDelegateWithBackAddIcon implements ShippingContract.View {
    private static final String SHIPPING_TYPE="shipping_type";
    private int type;
    ShippingAdapter adapter;
    private ShippingContract.Presenter presenter;
    private boolean isInit = true;

    @Override
    protected String titleName() {
        return "收货地址";
    }

    @Override
    protected int barColor() {
        return ContextCompat.getColor(getContext(), R.color.background_color);
    }

    @Override
    protected void onAddClick() {
        getSupportDelegate().start(new ShippingAddOrChangeDelegate());
    }

    @Override
    protected void initRecycleView(RecyclerView shippingRv) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        shippingRv.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_light
        );
        swipeRefreshLayout.setProgressViewOffset(true, 90, 300);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle myBundle = this.getArguments();
        if (myBundle!=null){
            type=myBundle.getInt(SHIPPING_TYPE);
        }
    }
    //ｔｙｐｅ为０者表示一般类型，ｔｙｐｅ为１从订单过度而来
    public static ShipDelegate create(int type) {
        final Bundle bundle = new Bundle();
        bundle.putInt(SHIPPING_TYPE, type);
        final ShipDelegate shipDelegate = new ShipDelegate();
        shipDelegate.setArguments(bundle);
        return shipDelegate;
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        presenter = new ShippingPresenter(this);
        presenter.subscribe();
        super.onBindView(savedInstanceState, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public void onRefresh() {
        presenter.getShippingItems(true);
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
    public void showSwipeRefresh() {
        getSwipeRefreshLayout().setRefreshing(true);
    }

    @Override
    public void onDataInit(final ArrayList<MultipleItemEntity> entities) {
        if (isInit) {
            adapter = new ShippingAdapter(entities);
            getRecyclerView().setAdapter(adapter);
            isInit = false;
            if (type==1) {
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        int id=entities.get(position).getField(ShippingFields.ID);
                        final IGlobalCallback<Integer> callback = CallBackManager.getInstance()
                                .getCallback(CallBackType.SHIPPING_ID);
                        callback.executeCallback(id);
                        getSupportDelegate().pop();
                    }
                });
            }
            if (entities.size() >= 10) {
                adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        presenter.getShippingItems(false);
                    }
                }, getRecyclerView());
            }
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    MultipleItemEntity entity = entities.get(position);
                    ShippingVo shippingVo = converEntity(entity);

                    if (view.getId() == R.id.shipping_edit) {
                        getSupportDelegate().start(ShippingAddOrChangeDelegate.newInsance(shippingVo));
                    } else if (view.getId() == R.id.shipping_delete) {
                        int id = entities.get(position).getField(ShippingFields.ID);
                        presenter.deleteShipById(id, position);
                    }
                }
            });
        } else
            adapter.setNewData(entities);
    }

    private ShippingVo converEntity(MultipleItemEntity entity) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setShippingId((Integer) entity.getField(ShippingFields.ID));
        shippingVo.setReceiverName((String) entity.getField(ShippingFields.NAME));
        shippingVo.setReceiverPhone((String) entity.getField(ShippingFields.PHONE));
        shippingVo.setReceiverMobile((String) entity.getField(ShippingFields.MOBLIE));
        shippingVo.setReceiverProvince((String) entity.getField(ShippingFields.PROVINCE));
        shippingVo.setReceiverCity((String) entity.getField(ShippingFields.CITY));
        shippingVo.setReceiverDistrict((String) entity.getField(ShippingFields.DISTRICT));
        shippingVo.setReceiverAddress((String) entity.getField(ShippingFields.ADDRESS));
        shippingVo.setReceiverZip((String) entity.getField(ShippingFields.ZIP));
        return shippingVo;
    }

    @Override
    public void cancalSwipeRefresh() {
        getSwipeRefreshLayout().setRefreshing(false);
    }

    @Override
    public void onDataAdd(ArrayList<MultipleItemEntity> datas) {
        adapter.addData(datas);
    }

    @Override
    public void onLoadMoreDataComplete() {
        adapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreDataFail() {
        adapter.loadMoreFail();
    }

    @Override
    public void onDelSuccess(int position) {
        adapter.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
