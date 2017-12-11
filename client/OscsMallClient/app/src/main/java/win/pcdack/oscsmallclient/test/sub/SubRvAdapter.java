package win.pcdack.oscsmallclient.test.sub;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Random;

import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.test.NewIndexItemType;

/**
 * Created by pcdack on 17-12-8.
 *
 */

public class SubRvAdapter extends MultipleRecyclerAdapter{
    private Random random=new Random();
    private int color[]={Color.BLUE,Color.RED,Color.YELLOW};
    protected SubRvAdapter(List<MultipleItemEntity> data) {
        super(data);

    }

    @Override
    protected void init() {
        addItemType(NewIndexItemType.CHILD_CATEGORY,R.layout.item_child_category);
        addItemType(NewIndexItemType.SUB_PRODUCT,R.layout.item_sub_product);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        String name;
        int id;
        String subTitle;
        String prices;
        String image;
        switch (holder.getItemViewType()){
            case NewIndexItemType.CHILD_CATEGORY:
                int mColor=color[random.nextInt(3)];
                Logger.d("color is "+mColor);
                name =entity.getField(SubFields.NAME);
                image=entity.getField(SubFields.IMAGE);
                subTitle=entity.getField(SubFields.CHILD_CATEGORY);
                holder.setText(R.id.child_category_image,image).setTextColor(R.id.child_category_image,mColor);
                holder.setText(R.id.my_root_category_text,name).setTextColor(R.id.my_root_category_text,mColor);
                holder.setText(R.id.my_child_category_text,subTitle);
                break;
            case NewIndexItemType.SUB_PRODUCT:
                image=entity.getField(SubFields.IMAGE);
                name=entity.getField(SubFields.NAME);
                subTitle=entity.getField(SubFields.SUB_TITLE);
                prices=entity.getField(SubFields.PRICE);
                holder.setText(R.id.product_cart_name,name);
                holder.setText(R.id.product_cart_sub_title,subTitle);
                holder.setText(R.id.product_cart_price,"$"+prices);
                Glide.with(mContext)
                        .load(image)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.product_cart_image_view));
                break;
        }
    }
}
