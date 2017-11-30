package win.pcdack.creamsoda_core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.R;
import win.pcdack.creamsoda_core.ui.banner.BannerCreator;
import win.pcdack.creamsoda_core.ui.camera.RequestCodes;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;

/**
 * Created by pcdack on 17-10-21.
 *
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener{
    private boolean isInitBanners=false;
    private ArrayList<Integer> bannerIds;
    protected static final RequestOptions OPTIONS=new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();
    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    protected void init() {
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNERS, R.layout.item_multiple_banner);
        addItemType(ItemType.NORMAL_PRODUCT,R.layout.item_multiple_normal_product);
        addItemType(ItemType.FUNC_ITEM, R.layout.item_show_function_container);
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> datas){
        return new MultipleRecyclerAdapter(datas);
    }
    public static MultipleRecyclerAdapter create(DataCoverter coverter){
        return new MultipleRecyclerAdapter(coverter.convert());
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final int price;
        final ArrayList<String> banners;
        final ArrayList<String> functionImages;
        final ArrayList<String> functionTexts;
        final ArrayList<String> functionUrls;
        switch (holder.getItemViewType()){
            case ItemType.BANNERS:
                if (!isInitBanners){
                    banners=entity.getField(MultipleFields.BANNERS);
                    bannerIds=entity.getField(MultipleFields.ID);
                    final ConvenientBanner<String> convenientBanner=holder.getView(R.id.banner_item);
                    BannerCreator.setDefault(convenientBanner,banners,this);
                    isInitBanners=true;
                }
                break;
            case ItemType.TEXT:
                text=entity.getField(MultipleFields.NAME);
                holder.setText(R.id.single_text,text);
                break;
            case ItemType.IMAGE:
                imageUrl=entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.single_image));
                break;
            case ItemType.TEXT_IMAGE:
                text=entity.getField(MultipleFields.TEXT);
                imageUrl=entity.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.multiple_text,text);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.multiple_img));
                break;
            case ItemType.NORMAL_PRODUCT:
                text=entity.getField(MultipleFields.TEXT);
                imageUrl=entity.getField(MultipleFields.IMAGE_URL);
                price=entity.getField(MultipleFields.PIRCE);
                holder.setText(R.id.mult_product_text,text);
                holder.setText(R.id.mult_product_price,"$"+String.valueOf(price));
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) holder.getView(R.id.mult_product_img));
                break;
            case ItemType.FUNC_ITEM:
                functionImages=entity.getField(MultipleFields.IMAGE_URL);
                functionTexts=entity.getField(MultipleFields.TEXT);
                functionUrls=entity.getField(MultipleFields.URL);
                initItemImageAndText(functionImages,functionTexts,holder);
                break;

            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getSpanSize();
    }

    @Override
    public void onItemClick(int position) {
        if (bannerIds!=null)
            CallBackManager.getInstance().getCallback(RequestCodes.BANNER).executeCallback(bannerIds.get(position));
    }
    // TODO: 17-11-17 去除硬编码
    private void initItemImageAndText(ArrayList<String> functionImages, ArrayList<String> functionTexts, MultipleViewHolder holder) {
        holder.setText(R.id.function_one_text,functionTexts.get(0));
        holder.setText(R.id.function_two_text,functionTexts.get(1));
        holder.setText(R.id.function_three_text,functionTexts.get(2));
        holder.setText(R.id.function_four_text,functionTexts.get(3));
        holder.setText(R.id.function_five_text,functionTexts.get(4));
        holder.setText(R.id.function_six_text,functionTexts.get(5));
        holder.setText(R.id.function_seven_text,functionTexts.get(6));
        holder.setText(R.id.function_eight_text,functionTexts.get(7));
        holder.setText(R.id.function_nine_text,functionTexts.get(8));
        holder.setText(R.id.function_ten_text,functionTexts.get(9));
        final ImageView imageView1=holder.getView(R.id.function_one_img);
        final ImageView imageView2=holder.getView(R.id.function_two_img);
        final ImageView imageView3=holder.getView(R.id.function_three_img);
        final ImageView imageView4=holder.getView(R.id.function_four_img);
        final ImageView imageView5=holder.getView(R.id.function_five_img);
        final ImageView imageView6=holder.getView(R.id.function_six_img);
        final ImageView imageView7=holder.getView(R.id.function_seven_img);
        final ImageView imageView8=holder.getView(R.id.function_eight_img);
        final ImageView imageView9=holder.getView(R.id.function_nine_img);
        final ImageView imageView10=holder.getView(R.id.function_ten_img);
        Glide.with(mContext).load(functionImages.get(0)).apply(OPTIONS).into(imageView1);
        Glide.with(mContext).load(functionImages.get(1)).apply(OPTIONS).into(imageView2);
        Glide.with(mContext).load(functionImages.get(2)).apply(OPTIONS).into(imageView3);
        Glide.with(mContext).load(functionImages.get(3)).apply(OPTIONS).into(imageView4);
        Glide.with(mContext).load(functionImages.get(4)).apply(OPTIONS).into(imageView5);
        Glide.with(mContext).load(functionImages.get(5)).apply(OPTIONS).into(imageView6);
        Glide.with(mContext).load(functionImages.get(6)).apply(OPTIONS).into(imageView7);
        Glide.with(mContext).load(functionImages.get(7)).apply(OPTIONS).into(imageView8);
        Glide.with(mContext).load(functionImages.get(8)).apply(OPTIONS).into(imageView9);
        Glide.with(mContext).load(functionImages.get(9)).apply(OPTIONS).into(imageView10);
    }
}
