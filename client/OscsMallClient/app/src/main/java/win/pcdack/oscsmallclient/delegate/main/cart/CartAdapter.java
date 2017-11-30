package win.pcdack.oscsmallclient.delegate.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-10-27.
 *
 */

public class CartAdapter extends MultipleRecyclerAdapter {

    public CartAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    @Override
    protected void init() {
        addItemType(CartItemType.LIST_CART, R.layout.item_cart);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        switch (entity.getItemType()){
            case CartItemType.LIST_CART:
                //初始数据填充
                String title=entity.getField(MultipleFields.NAME);
                int num=entity.getField(MultipleFields.QUANTITY);
                String desc=entity.getField(MultipleFields.TEXT);
                boolean checked=entity.getField(MultipleFields.CHECKED);
                final IconTextView checkedTv=holder.getView(R.id.select_icon_cart);
                checkedTv.setTextColor(checked? ContextCompat.getColor(mContext,R.color.view_color): Color.GRAY);
                holder.setText(R.id.cart_item_title, title)
                        .setText(R.id.cart_num,String.valueOf(num))
                        .setText(R.id.cart_item_desc,desc)
                        .addOnClickListener(R.id.select_icon_cart)
                        .addOnClickListener(R.id.cart_add_icon)
                        .addOnClickListener(R.id.cart_les_icon);
                String imageUrl=entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.cart_item_img));
                break;
            default:
                break;
        }
    }
}
