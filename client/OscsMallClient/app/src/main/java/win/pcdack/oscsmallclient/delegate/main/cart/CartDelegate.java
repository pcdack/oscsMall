package win.pcdack.oscsmallclient.delegate.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.bottom.BottomItemDelegate;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.BaseDecoration;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.order.OrderCreateDelegate;

/**
 * Created by pcdack on 17-11-19.
 *
 */

public class CartDelegate extends BottomItemDelegate implements CartContract.View{
    @BindView(R.id.delete_carts_tv)
    TextView deleteCartsTv;
    @BindView(R.id.cart_rv)
    RecyclerView cartRv;
    @BindView(R.id.icon_is_select_all)
    TextView selectAllIcon;
    @OnClick(R.id.icon_is_select_all)
    void onIconIsSelectAll(){
        if (presenter!=null)
            if (presenter.isAllSelect())
                presenter.recyclerItemUnSelectAll();
            else
                presenter.recyclerItemSelectAll();
    }
    @BindView(R.id.total_price)
    TextView totalPrice;
    private CartContract.Presenter presenter;
    private CartAdapter adapter;
    @OnClick(R.id.delete_carts_tv)
    void onDeleteIconClick(){
        presenter.deleteItems();
    }
    @OnClick(R.id.settlement_btn)
    void onSettleMentBtnClick(){
        getParentDelegate().getSupportDelegate().start(new OrderCreateDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        presenter=new CartPresenter(this);
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initRecycleView();
        presenter.subscribe();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecycleView();
        presenter.subscribe();

    }
    private void initRecycleView() {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        cartRv.setLayoutManager(manager);
        cartRv.addItemDecoration(new BaseDecoration(ContextCompat.getColor(getContext(),R.color.gray),1));
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
    public void setCartItems(List<MultipleItemEntity> entities) {
        adapter=new CartAdapter(entities);
        cartRv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cart_add_icon){
                    presenter.recyclerItemAddOnClick(position);
                }else if (view.getId() == R.id.cart_les_icon){
                    presenter.recyclerItemLesOnClick(position);
                }else if (view.getId() == R.id.select_icon_cart){
                    presenter.recyclerItemSelectIconClick(position);
                }
            }
        });
    }

    @Override
    public void updateCartItems(List<MultipleItemEntity> entities) {
        adapter.replaceData(entities);
    }


    @Override
    public void setAllSelectStatus(boolean turnOn) {
        selectAllIcon.setTextColor(turnOn?ContextCompat.getColor(getContext(),R.color.view_color): Color.GRAY);
    }

    @Override
    public void setAllPrices(int num) {
        totalPrice.setText("$"+String.valueOf(num));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

}
