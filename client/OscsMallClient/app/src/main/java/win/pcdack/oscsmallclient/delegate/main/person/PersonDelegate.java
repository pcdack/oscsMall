package win.pcdack.oscsmallclient.delegate.main.person;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.GlobalUrlVal;
import win.pcdack.creamsoda_core.delegates.bottom.BottomItemDelegate;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.util.image.ImageUtils;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.main.person.order.OrderDelegate;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShipDelegate;
import win.pcdack.oscsmallclient.delegate.main.person.user.UserInfoChangeDelegate;

/**
 * Created by pcdack on 17-11-22.
 *
 */

@SuppressWarnings("unused")
public class PersonDelegate extends BottomItemDelegate implements PersonContract.View{

    private PersonContract.Presenter presenter;
    @BindView(R.id.user_header)
    ImageView headerImageView;
    @BindView(R.id.user_name)
    TextView userNameTv;
    @OnClick({R.id.user_name,R.id.user_header})
    void onUserInfoClick(){
        getParentDelegate().getSupportDelegate().start(new UserInfoChangeDelegate());
    }
    @OnClick(R.id.order_center)
    void onOrderCenterClick(){
        getParentDelegate().getSupportDelegate().start(OrderDelegate.newInsance(GlobalUrlVal.ORDER_LIST));
    }
    @OnClick(R.id.un_pay)
    void onUnPayClick(){
        getParentDelegate().getSupportDelegate().start(OrderDelegate.newInsance(GlobalUrlVal.UNPAY_ORDER_LIST));

    }
    @OnClick(R.id.un_receipt)
    void onReceiptClick(){
        getParentDelegate().getSupportDelegate().start(OrderDelegate.newInsance(GlobalUrlVal.NO_RECEIPT_ORDER_LIST));
    }
    @OnClick(R.id.shipping)
    void onShipClick(){
        getParentDelegate().getSupportDelegate().start(new ShipDelegate());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_person;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        presenter=new PersonPresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        presenter.subscribe();
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
    public void setImageViewBase64(String content) {
        if (TextUtils.isEmpty(content))
            headerImageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
        ImageUtils.getImgByBase64(content,headerImageView);
    }

    @Override
    public void setUserName(String userName) {
        userNameTv.setText(userName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }
}
