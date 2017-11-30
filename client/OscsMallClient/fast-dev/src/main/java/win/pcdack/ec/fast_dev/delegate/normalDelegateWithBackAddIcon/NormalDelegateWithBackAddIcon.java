package win.pcdack.ec.fast_dev.delegate.normalDelegateWithBackAddIcon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.ec.fast_ec.R;
import win.pcdack.ec.fast_ec.R2;

/**
 * Created by pcdack on 17-11-23.
 *
 */

public abstract class NormalDelegateWithBackAddIcon extends CreamSodaDelegate implements SwipeRefreshLayout.OnRefreshListener{
    protected abstract String titleName();
    protected abstract int barColor();
    protected abstract void onAddClick();
    protected abstract void initRecycleView(RecyclerView shippingRv);
    protected abstract void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout);
    @BindView(R2.id.title_tv)
    TextView titleTv;
    @BindView(R2.id.m_toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.shipping_rv)
    RecyclerView shippingRv;
    @BindView(R2.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public Object setLayout() {
        return R.layout.delegate_with_back_add_icon;
    }
    public SwipeRefreshLayout getSwipeRefreshLayout(){
        return swipeRefreshLayout;
    }
    public RecyclerView getRecyclerView(){return shippingRv;}
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecycleView(shippingRv);
        initSwipeRefresh(swipeRefreshLayout);
        if (titleTv!=null)
            titleTv.setText(titleName());
        mToolbar.setBackgroundColor(barColor());
    }




    @OnClick({R2.id.shipping_back_icon, R2.id.shipping_add})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.shipping_back_icon) {
            getSupportDelegate().pop();
        } else if (i == R.id.shipping_add) {
            onAddClick();
        }
    }
}
