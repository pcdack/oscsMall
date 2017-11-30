package win.pcdack.oscsmallclient.delegate.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.camera.RequestCodes;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.BaseDecoration;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;
import win.pcdack.creamsoda_core.util.callback.CallBackType;
import win.pcdack.creamsoda_core.util.callback.IGlobalCallback;
import win.pcdack.oscsmallclient.GlobalVal;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.main.cart.CartAdapter;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShipDelegate;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShippingFields;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class OrderCreateDelegate extends CreamSodaDelegate implements OrderCreateContract.View {
    private int shipId;
    private OrderCreateContract.Presenter presenter;
    @BindView(R.id.back_icon)
    IconTextView backIcon;
    @BindView(R.id.receiver)
    TextView receiver;
    @BindView(R.id.receiver_phone_num)
    TextView receiverPhoneNum;
    @BindView(R.id.receiver_add)
    TextView receiverAdd;
    @BindView(R.id.my_shipping)
    LinearLayout myShipping;
    @BindView(R.id.order_goods_rv)
    RecyclerView orderGoodsRv;
    @BindView(R.id.order_total_price)
    TextView orderTotalPrice;
    @BindView(R.id.create_order)
    Button createOrder;
    @OnClick(R.id.create_order)
    void createOrderClicked(){
        presenter.createOrder(shipId);
    }

    @OnClick(R.id.back_icon)
    void onBackIconClick(){
        getSupportDelegate().pop();
    }
    @OnClick(R.id.my_shipping)
    void onShipChangeClick(){
        getSupportDelegate().start(ShipDelegate.create(1));
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        presenter=new OrderCreatePresenter(this);
        presenter.getShipDetail();
        presenter.getCartDetail();
        initRecycler();
        CallBackManager.getInstance().addCallback(CallBackType.SHIPPING_ID, new IGlobalCallback<Integer>() {
            @Override
            public void executeCallback(@Nullable Integer args) {
                if (args!=null)
                    presenter.updateShipInfo(args);
            }

        });

    }

    private void initRecycler() {
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        orderGoodsRv.setLayoutManager(linearLayoutManager);
        orderGoodsRv.addItemDecoration(new BaseDecoration(ContextCompat.getColor(getContext(),R.color.gray),3));
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
    public void setShipDetail(MultipleItemEntity entity) {
        shipId=entity.getField(ShippingFields.ID);
        String receiverStr=entity.getField(ShippingFields.NAME);
        String receiverPhone=entity.getField(ShippingFields.MOBLIE);
        String receiverAddress=entity.getField(ShippingFields.ADDRESS);
        receiver.setText(receiverStr);
        receiverPhoneNum.setText(String.valueOf(receiverPhone));
        receiverAdd.setText(receiverAddress);
    }

    @Override
    public void setCartDetail(ArrayList<MultipleItemEntity> entities) {
        CartAdapter adapter=new CartAdapter(entities);
        orderGoodsRv.setAdapter(adapter);
    }

    @Override
    public void setAllPrices(int prices) {
        orderTotalPrice.setText(String.valueOf(prices));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unSubscribe();
    }
}
