package win.pcdack.oscsmallclient.delegate.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.base.BaseItemType;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class ContentAdapter extends MultipleRecyclerAdapter {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    protected ContentAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(BaseItemType.LIST_CONTENT, R.layout.item_list_content);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        switch (holder.getItemViewType()){
            case BaseItemType.LIST_CONTENT:
                final String name=entity.getField(MultipleFields.NAME);
                final int id=entity.getField(MultipleFields.ID);
                final String imgUrl=entity.getField(MultipleFields.IMAGE_URL);
                final AppCompatImageView img=holder.getView(R.id.content_img);
                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(OPTIONS)
                        .into(img);
                holder.setText(R.id.content_text,name);
                // TODO: 17-10-23 点击事件处理,需要ID字段
                break;
            default:
                break;
        }
    }
}
