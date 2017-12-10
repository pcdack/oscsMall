package win.pcdack.oscsmallclient.test;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-12-8.
 *
 */

public class SubCategoryRvAdapter extends MultipleRecyclerAdapter{

    protected SubCategoryRvAdapter(List<MultipleItemEntity> data) {
        super(data);

    }

    @Override
    protected void init() {
        addItemType(TestItemType.CHILD_CATEGORY,R.layout.item_child_category);
        addItemType(TestItemType.SUB_PRODUCT,R.layout.item_sub_product);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        String name;
        int id;
        String subTitle;
        String prices;
        String image;
        switch (holder.getItemViewType()){
            case TestItemType.CHILD_CATEGORY:
                name =entity.getField(SubFields.NAME);
                subTitle=entity.getField(SubFields.CHILD_CATEGORY);
                holder.setText(R.id.my_root_category_text,name);
                holder.setText(R.id.my_child_category_text,subTitle);
                break;
            case TestItemType.SUB_PRODUCT:
                image=entity.getField(SubFields.IMAGE);
                name=entity.getField(SubFields.NAME);
                subTitle=entity.getField(SubFields.SUB_TITLE);
                prices=entity.getField(SubFields.PRICE);
                holder.setText(R.id.product_cart_name,name);
                holder.setText(R.id.product_cart_sub_title,subTitle);
                holder.setText(R.id.product_cart_price,prices);
                Glide.with(mContext)
                        .load(image)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.product_cart_image_view));
                break;
        }
    }
}
