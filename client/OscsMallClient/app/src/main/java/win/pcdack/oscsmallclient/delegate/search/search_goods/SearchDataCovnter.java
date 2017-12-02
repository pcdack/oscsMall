package win.pcdack.oscsmallclient.delegate.search.search_goods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.delegate.search.SearchType;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class SearchDataCovnter extends DataCoverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> multipleItemEntities = new ArrayList<>();

        JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("list");
        final int size = array.size();
        MultipleItemEntity entity;
        for (int i = 0; i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            final int id = object.getInteger("id");
            final String name = object.getString("name");
            final String subtitle=object.getString("subtitle");
            final String mainImage=object.getString("main_image");
            final int price=object.getInteger("price");
            final int status = object.getInteger("status");
            final int span_size=object.getInteger("span_size");
            final int index_kind=object.getInteger("index_kind");
            entity=MultipleItemEntity.builder()
                    .setItemType(SearchType.GOODS_SEARCH)
                    .setField(MultipleFields.ITEM_TYPE, SearchType.GOODS_SEARCH)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.TEXT,subtitle)
                    .setField(MultipleFields.IMAGE_URL,mainImage)
                    .setField(MultipleFields.PIRCE,price)
                    .build();
            multipleItemEntities.add(entity);
        }
        return multipleItemEntities;
    }
}
