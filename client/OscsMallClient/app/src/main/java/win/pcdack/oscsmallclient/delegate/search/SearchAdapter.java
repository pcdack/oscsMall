package win.pcdack.oscsmallclient.delegate.search;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;

/**
 *
 * Created by pcdack on 17-11-2.
 */

public class SearchAdapter extends MultipleRecyclerAdapter {

    public SearchAdapter(List<MultipleItemEntity> data) {
        super(data);

    }

    @Override
    protected void init() {
        addItemType(SearchType.ITEM_SEARCH, R.layout.search_item_layout);
        addItemType(SearchType.GOODS_SEARCH, R.layout.item_search_goods);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        switch (entity.getItemType()) {
            case SearchType.ITEM_SEARCH:
                holder.setText(R.id.search_history,(String)entity.getField(MultipleFields.TEXT));
                break;
            case SearchType.GOODS_SEARCH:
                int productId=entity.getField(MultipleFields.ID);
                String name=entity.getField(MultipleFields.NAME);
                String subtitle=entity.getField(MultipleFields.TEXT);
                String imageUrl=entity.getField(MultipleFields.IMAGE_URL);
                int price=entity.getField(MultipleFields.PIRCE);
                holder.setText(R.id.name,name);
                holder.setText(R.id.subtitle,subtitle);
                holder.setText(R.id.price,"$"+String.valueOf(price));
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.goods_image));
                break;
            default:
                break;
        }
    }
}
