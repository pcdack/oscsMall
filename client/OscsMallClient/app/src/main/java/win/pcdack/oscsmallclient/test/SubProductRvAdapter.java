package win.pcdack.oscsmallclient.test;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-12-9.
 *
 */

public class SubProductRvAdapter extends BaseQuickAdapter<SubProductItem,BaseViewHolder>{
    public SubProductRvAdapter(@Nullable List<SubProductItem> data) {
        super(R.layout.item_sub_product,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubProductItem item) {
        helper.setText(R.id.product_cart_name,item.getName());
        helper.setText(R.id.product_cart_sub_title,item.getSubTitle());
        helper.setText(R.id.product_cart_price,item.getPrice());
    }
}
