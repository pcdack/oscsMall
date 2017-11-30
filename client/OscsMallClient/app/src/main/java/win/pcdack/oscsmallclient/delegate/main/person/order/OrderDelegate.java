package win.pcdack.oscsmallclient.delegate.main.person.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.net.RestClient;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.ec.fast_dev.delegate.normalDelegateWithBackIcon.NormalDelegateWithBackIcon;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-11-23.
 *
 */

public class OrderDelegate extends NormalDelegateWithBackIcon implements BaseQuickAdapter.RequestLoadMoreListener {
    private final static String URL_KEY = "url";
    private ArrayList<MultipleItemEntity> entities = null;
    private OrderAdapter orderAdapter = null;
    private String url;
    private int pageNum = 1;

    @Override
    protected String titleName() {
        return "订单详情";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            url = args.getString(URL_KEY);
        }
    }

    @Override
    protected int barColor() {
        return ContextCompat.getColor(getContext(), R.color.background_color);
    }

    @Override
    protected void initRecycleView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public static OrderDelegate newInsance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, url);
        final OrderDelegate orderDelegate = new OrderDelegate();
        orderDelegate.setArguments(bundle);
        return orderDelegate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
        getData(true);
        if (orderAdapter != null)
            orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getData(false);
                }
            }, getRecyclerView());
    }

    private void getData(final boolean hasFoucs) {
        if (!hasFoucs)
            pageNum++;
        RestClient.builder()
                .url(url)
                .params("pageNum", pageNum)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        entities = new OrderDataCoventer().setJsonData(response).convert();
                        orderAdapter = new OrderAdapter(entities);
                        getRecyclerView().setAdapter(orderAdapter);
                        if (entities.size() >= 10)
                            orderAdapter.setOnLoadMoreListener(OrderDelegate.this, getRecyclerView());
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        getProxyActivity().showErrorMassage(errorCode, errorMsg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getProxyActivity().showFailureMassage();
                    }
                })
                .build().post();
    }

    @Override
    public void onLoadMoreRequested() {
        getData(false);
    }
}
