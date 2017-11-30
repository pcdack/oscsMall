package win.pcdack.oscsmallclient.delegate.main.person.ship;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import win.pcdack.GlobalUrlVal;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.net.RestClient;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.ec.fast_dev.list.ListAdapter;
import win.pcdack.ec.fast_dev.list.ListBean;
import win.pcdack.ec.fast_dev.list.ListItemType;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-10-30.
 *
 */

public class ShippingAddOrChangeDelegate extends CreamSodaDelegate {
    private final static String NAME="NAME";
    private final static String PHONE="PHONE";
    private final static String MOBILE="MOBILE";
    private final static String PROVINCE="PROVINCE";
    private final static String CITY="CITY";
    private final static String DISTRICT="DISTRICT";
    private final static String ADDRESS="ADDRESS";
    private final static String ZIP="ZIP";
    private final static String ID="ID";
    private String receiverNameStr="空";
    private String receiverPhoneStr="空";
    private String receiverMobileStr="空";
    private String receiverProvinceStr="空";
    private String receiverCityStr="空";
    private String receiverDistrictStr="空";
    private String receiverAddressStr="空";
    private String receiverZipStr="空";
    private int shippingId;
    private boolean addFlag=false;
    @OnClick(R.id.shipping_change_back_icon)
    void backIconClick(){
        getSupportDelegate().pop();
    }
    @BindView(R.id.my_shipping_change_rv)
    RecyclerView myRv;
    @OnClick(R.id.sure_to_change)
    void onChangeClick(){
        ShippingVo shippingVo=new ShippingVo();
        shippingVo.setReceiverName(been.get(0).getValue());
        shippingVo.setReceiverPhone(been.get(1).getValue());
        shippingVo.setReceiverMobile(been.get(2).getValue());
        shippingVo.setReceiverProvince(been.get(3).getValue());
        shippingVo.setReceiverCity(been.get(4).getValue());
        shippingVo.setReceiverDistrict(been.get(5).getValue());
        shippingVo.setReceiverAddress(been.get(6).getValue());
        shippingVo.setReceiverZip(been.get(7).getValue());
        if (addFlag) {
            shippingVo.setShippingId(shippingId);
            RestClient.builder()
                    .url(GlobalUrlVal.SHIP_UPDATE)
                    .params("id",shippingVo.getShippingId())
                    .params("receiverName",shippingVo.getReceiverName())
                    .params("receiverMobile",shippingVo.getReceiverMobile())
                    .params("receiverPhone",shippingVo.getReceiverPhone())
                    .params("receiverProvince",shippingVo.getReceiverProvince())
                    .params("receiverCity",shippingVo.getReceiverCity())
                    .params("receiverDistrict",shippingVo.getReceiverDistrict())
                    .params("receiverAddress",shippingVo.getReceiverAddress())
                    .params("receiverZip",shippingVo.getReceiverZip())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            getProxyActivity().showSuccessMassage("更新成功");
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
                    .build()
                    .post();

        }else {
            RestClient.builder()
                    .url(GlobalUrlVal.SHIP_ADD)
                    .params("receiverName",shippingVo.getReceiverName())
                    .params("receiverMobile",shippingVo.getReceiverMobile())
                    .params("receiverPhone",shippingVo.getReceiverPhone())
                    .params("receiverProvince",shippingVo.getReceiverProvince())
                    .params("receiverCity",shippingVo.getReceiverCity())
                    .params("receiverDistrict",shippingVo.getReceiverDistrict())
                    .params("receiverAddress",shippingVo.getReceiverAddress())
                    .params("receiverZip",shippingVo.getReceiverZip())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            getProxyActivity().showSuccessMassage("新建成功");
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int errorCode, String errorMsg) {
                            getProxyActivity().showErrorMassage(errorCode,errorMsg);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            getProxyActivity().showFailureMassage();
                        }
                    })
                    .build()
                    .post();
        }
    }
    private List<ListBean> been=new ArrayList<>();

    public static ShippingAddOrChangeDelegate newInsance(ShippingVo shippingVo){
        final Bundle bundle=new Bundle();
        bundle.putString(NAME,shippingVo.getReceiverName());
        bundle.putString(PHONE,shippingVo.getReceiverPhone());
        bundle.putString(MOBILE,shippingVo.getReceiverMobile());
        bundle.putString(PROVINCE,shippingVo.getReceiverProvince());
        bundle.putString(CITY,shippingVo.getReceiverCity());
        bundle.putString(DISTRICT,shippingVo.getReceiverDistrict());
        bundle.putString(ADDRESS,shippingVo.getReceiverAddress());
        bundle.putString(ZIP,shippingVo.getReceiverZip());
        bundle.putInt(ID,shippingVo.getShippingId());
        final ShippingAddOrChangeDelegate delegate=new ShippingAddOrChangeDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args=getArguments();
        if (args.getString(NAME)!=null){
            addFlag=true;
            receiverNameStr=args.getString(NAME);
            receiverPhoneStr=args.getString(PHONE);
            receiverMobileStr=args.getString(MOBILE);
            receiverProvinceStr=args.getString(PROVINCE);
            receiverCityStr=args.getString(CITY);
            receiverDistrictStr=args.getString(DISTRICT);
            receiverAddressStr=args.getString(ADDRESS);
            receiverZipStr=args.getString(ZIP);
            shippingId=args.getInt(ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shipping_add_change;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRv();
        initData();
        ListAdapter mAdapter = new ListAdapter(been);
        myRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                if (been.size()>0){
                    final ListBean bean=been.get(position);
                    AlertDialog.Builder dialogbuilder=new AlertDialog.Builder(getContext());
                    dialogbuilder.setTitle(bean.getText());
                    final EditText myEditText=new EditText(getContext());
                    dialogbuilder.setView(myEditText);
                    dialogbuilder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String result=myEditText.getText().toString();
                            bean.setmValue(result);
                            adapter.notifyItemChanged(position);
                        }
                    });
                    dialogbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog=dialogbuilder.create();
                    dialog.show();
                }
            }
        });
    }

    private void initRv() {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        myRv.setLayoutManager(manager);
    }

    private void initData() {
        final ListBean receiverName = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收件人")
                .setValue(receiverNameStr)
                .build();
        final ListBean receiverPhone = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("电话")
                .setValue(receiverPhoneStr)
                .build();
        final ListBean receiverMobile = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("手机")
                .setValue(receiverMobileStr)
                .build();
        final ListBean receiverProvince = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("省份")
                .setValue(receiverProvinceStr)
                .build();
        final ListBean receiverCity = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(5)
                .setText("城市")
                .setValue(receiverCityStr)
                .build();
        final ListBean receiverDistrict = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(6)
                .setText("地区")
                .setValue(receiverDistrictStr)
                .build();
        final ListBean receiverAddress = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(7)
                .setText("地址")
                .setValue(receiverAddressStr)
                .build();
        final ListBean receiverZip = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(8)
                .setText("邮编")
                .setValue(receiverZipStr)
                .build();

        been.add(receiverName);
        been.add(receiverPhone);
        been.add(receiverMobile);
        been.add(receiverProvince);
        been.add(receiverCity);
        been.add(receiverDistrict);
        been.add(receiverAddress);
        been.add(receiverZip);

    }
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
