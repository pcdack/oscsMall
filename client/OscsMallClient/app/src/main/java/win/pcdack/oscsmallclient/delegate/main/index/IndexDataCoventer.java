package win.pcdack.oscsmallclient.delegate.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import okhttp3.Interceptor;
import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.ItemType;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.DomeUrlAndText;
import win.pcdack.oscsmallclient.entity.ProductBannerVo;

import static android.R.attr.name;

/**
 * Created by pcdack on 17-11-15.
 *
 */

public class IndexDataCoventer extends DataCoverter {
    private ArrayList<ProductBannerVo> banners=new ArrayList<>();
    private boolean bannerEndFlag=false;
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray myProductJsonArray= JSON.parseObject(getJsonData()).getJSONArray("list");
        final int size=myProductJsonArray.size();


        for (int i = 0; i < size; i++) {
            JSONObject itemJson=myProductJsonArray.getJSONObject(i);
            final int index_kind=itemJson.getInteger("index_kind");
            final int span_size=itemJson.getInteger("span_size");
            //公共信息
            final int id=itemJson.getInteger("id");
            final String name=itemJson.getString("name");
            final String subtitle=itemJson.getString("subtitle");
            final String main_image=itemJson.getString("main_image");
            //由于项目的原因所以imageHost没软用，因为图片都是从互联网上copy过来的
            final int price=itemJson.getInteger("price");
//            final int status=itemJson.getInteger("status");
            ProductBannerVo bannerVo;
            if (index_kind==ItemType.BANNERS){
                bannerVo=new ProductBannerVo();
                bannerVo.setId(id);
                bannerVo.setImageUrl(main_image);
                banners.add(bannerVo);
            }else {
                if (!bannerEndFlag){
                    if (banners.size()>0){
                        ArrayList<String> bannerImageUrls=new ArrayList<>();
                        ArrayList<Integer> bannerIds=new ArrayList<>();
                        for (ProductBannerVo banner : banners) {
                            bannerImageUrls.add(banner.getImageUrl());
                            bannerIds.add(banner.getId());
                        }
                        final MultipleItemEntity entity = MultipleItemEntity.builder()
                                .setField(MultipleFields.BANNERS,bannerImageUrls)
                                .setField(MultipleFields.ID,bannerIds)
                                .setField(MultipleFields.ITEM_TYPE, ItemType.BANNERS)
                                .setField(MultipleFields.SPAN_SIZE, 4)
                                .build();
                        ENTITIES.add(entity);
                        // TODO: 17-11-16 服务器追加相关数据
//                        initFunctionIconAndText();
                    }
                }
                bannerEndFlag=true;
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, index_kind)
                        .setField(MultipleFields.IMAGE_URL, main_image)
                        .setField(MultipleFields.NAME, name)
                        .setField(MultipleFields.ID,id)
                        .setField(MultipleFields.TEXT, subtitle)
                        .setField(MultipleFields.PIRCE, price)
                        .setField(MultipleFields.SPAN_SIZE, span_size)
                        .build();
                ENTITIES.add(entity);
            }
        }


        return ENTITIES;
    }

//    private void initFunctionIconAndText() {
//        final int size=DomeUrlAndText.IMG_URLS.length;
//        ArrayList<String> imageUrls=new ArrayList<>();
//        ArrayList<String> texts=new ArrayList<>();
//        ArrayList<String> urls=new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            imageUrls.add(DomeUrlAndText.IMG_URLS[i]);
//            texts.add(DomeUrlAndText.TEXTS[i]);
//            urls.add(DomeUrlAndText.URLS[i]);
//        }
//        MultipleItemEntity entity=MultipleItemEntity.builder()
//                .setField(MultipleFields.ITEM_TYPE,ItemType.FUNC_ITEM)
//                .setField(MultipleFields.IMAGE_URL,imageUrls)
//                .setField(MultipleFields.TEXT,texts)
//                .setField(MultipleFields.URL,urls)
//                .setField(MultipleFields.SPAN_SIZE,4)
//                .build();
//        ENTITIES.add(entity);
//
//    }
}
