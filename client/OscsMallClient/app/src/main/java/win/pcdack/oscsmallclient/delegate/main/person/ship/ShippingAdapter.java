package win.pcdack.oscsmallclient.delegate.main.person.ship;

import android.text.TextUtils;

import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.main.person.PersonType;

/**
 * Created by pcdack on 17-10-30.
 *
 */

public class ShippingAdapter extends MultipleRecyclerAdapter {

    protected ShippingAdapter(List<MultipleItemEntity> data) {
        super(data);

    }

    @Override
    protected void init() {
        addItemType(PersonType.SHIPPING_CART, R.layout.item_shipping);
        setSpanSizeLookup(this);
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        switch (holder.getItemViewType()){
            case PersonType.SHIPPING_CART:
                final int shippingId=entity.getField(ShippingFields.ID);
                final String receiverName=entity.getField(ShippingFields.NAME);
                final String receiverPhone=entity.getField(ShippingFields.PHONE);
                final String receiverMobile=entity.getField(ShippingFields.MOBLIE);
                final String receiverProvince=entity.getField(ShippingFields.PROVINCE);
                final String receiverCity=entity.getField(ShippingFields.CITY);
                final String receiverDistrict=entity.getField(ShippingFields.DISTRICT);
                final String receiverAddress=entity.getField(ShippingFields.ADDRESS);
                final String receiverZip=entity.getField(ShippingFields.ZIP);
                holder.setText(R.id.receiver_name_tv,receiverName);
                if (TextUtils.isEmpty(receiverMobile))
                    holder.setText(R.id.receiver_phone_tv,receiverPhone);
                else
                    holder.setText(R.id.receiver_phone_tv,receiverMobile);
                holder.setText(R.id.receiver_address,
                        receiverProvince+receiverCity+receiverDistrict+receiverAddress);
                holder.addOnClickListener(R.id.shipping_delete)
                        .addOnClickListener(R.id.shipping_edit);
                break;
            default:
                break;
        }
    }
}
