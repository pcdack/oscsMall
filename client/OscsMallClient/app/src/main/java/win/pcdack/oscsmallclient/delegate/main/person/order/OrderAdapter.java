package win.pcdack.oscsmallclient.delegate.main.person.order;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.main.person.PersonType;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShippingVo;

/**
 * Created by pcdack on 17-10-29.
 *
 */

class OrderAdapter extends MultipleRecyclerAdapter {

    OrderAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    @Override
    protected void init() {
        addItemType(PersonType.ORDER_CART, R.layout.item_order_layout);
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        switch (holder.getItemViewType()) {
            case PersonType.ORDER_CART:
                final String paymentTypeDesc=entity.getField(OrderField.PAYMENT_TYPE_DESC);
                final String statusDesc=entity.getField(OrderField.STATUSDESC);
                final int payment=entity.getField(OrderField.PAYMENT);
                // TODO: 17-11-23 服务器原因ｉｍａｇｅｈｏｓｔ是没用的
                @SuppressWarnings("unused")
                final String imageHost=entity.getField(OrderField.IMAGE_HOST);
                final ShippingVo shippingVo=entity.getField(OrderField.POSTION);
                ArrayList<OrderItemVo> vos=entity.getField(OrderField.ORDER_VOS);
                String imageUrl = null;
                holder.setText(R.id.pay_kind,paymentTypeDesc);
                holder.setText(R.id.pay_status,statusDesc);
                StringBuilder stb=new StringBuilder();
                for (OrderItemVo vo : vos) {
                    stb.append(vo.getProductName()).append("x").append(vo.getQuantity()).append("\n");
                    imageUrl=vo.getProductImage();
                }
                holder.setText(R.id.order_payment,"合计$"+payment);
                holder.setText(R.id.order_address,shippingVo.getReceiverProvince()
                        +shippingVo.getReceiverCity()
                        +shippingVo.getReceiverDistrict()
                        +shippingVo.getReceiverAddress());
                holder.setText(R.id.order_product_info,stb.toString());
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.order_product_pic));
                break;
        }
    }
}
