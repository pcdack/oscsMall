package win.pcdack.oscsmallclient.delegate.detail.goods.good_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.detail.goods.GoodsInfoBean;

/**
 * Created by pcdack on 17-11-4.
 *
 */

public class GoodsInfoLay extends CreamSodaDelegate {
    //    private static final String GOODS_INFO = "goods_info";
    private static final String GOODS_NAME = "goods_name";
    private static final String GOODS_PRICE = "goods_price";
    private static final String GOODS_STOCK = "goods_stock";
    private static final String GOODS_STATUS = "goods_status";
    private String goodsName;
    private int goodsPrice;
    private int goodStock;
    private int goodStatus;
    @BindView(R.id.goods_info_status)
    TextView goodsStatus;
    @BindView(R.id.goods_info_stock)
    TextView goodsStock;
    @BindView(R.id.goods_info_title)
    TextView goodsTitle;
    @BindView(R.id.goods_info_prices)
    TextView goodsPrices;

    public static GoodsInfoLay create(GoodsInfoBean bean) {
        final Bundle bundle = new Bundle();
        bundle.putString(GOODS_NAME, bean.getName());
        bundle.putInt(GOODS_PRICE, bean.getPrice());
        bundle.putInt(GOODS_STATUS, bean.getStatus());
        bundle.putInt(GOODS_STOCK, bean.getStock());
        GoodsInfoLay goodsInfo = new GoodsInfoLay();
        goodsInfo.setArguments(bundle);
        return goodsInfo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (!TextUtils.isEmpty(bundle.getString(GOODS_NAME))) {
            goodsName = bundle.getString(GOODS_NAME);
            goodsPrice = bundle.getInt(GOODS_PRICE);
            goodStatus = bundle.getInt(GOODS_STATUS);
            goodStock = bundle.getInt(GOODS_STOCK);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.goods_info_layout;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initData();
    }

    private void initData() {
        goodsStatus.setText(goodStatus == 1 ? "有货" : "无货");
        goodsStock.setText("库存" + goodStock);
        goodsTitle.setText(goodsName);
        goodsPrices.setText("$" + goodsPrice);
    }
}
