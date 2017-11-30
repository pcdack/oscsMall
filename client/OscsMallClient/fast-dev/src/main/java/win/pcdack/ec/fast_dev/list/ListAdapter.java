package win.pcdack.ec.fast_dev.list;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import win.pcdack.creamsoda_core.util.image.ImageUtils;
import win.pcdack.ec.fast_ec.R;


/**
 * Created by 傅令杰
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerInside();

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.item_arrow_layout);
        addItemType(ListItemType.ITEM_AVATAR,R.layout.item_arrow_with_avatar_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case ListItemType.ITEM_NORMAL:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());
                break;
            case ListItemType.ITEM_AVATAR:
                helper.setText(R.id.tv_arrow_with_avatar_text,item.getText());
                final ImageView avatar=helper.getView(R.id.arrow_with_avatar_imageview);
                ImageUtils.getImgByBase64(item.getImageUrl(),avatar);
                break;
            default:
                break;
        }
    }
}
