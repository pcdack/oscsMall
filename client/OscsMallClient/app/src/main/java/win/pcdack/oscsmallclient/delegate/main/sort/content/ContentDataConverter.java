package win.pcdack.oscsmallclient.delegate.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.ItemType;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.base.BaseItemType;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class ContentDataConverter extends DataCoverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        JSONArray jsonArray= JSON.parseArray(getJsonData());
        final int size=jsonArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject jsonObject=jsonArray.getJSONObject(i);
            final String name=jsonObject.getString("name");
            final int id=jsonObject.getInteger("id");
            final String imgUrl=jsonObject.getString("img");

            final MultipleItemEntity entity= MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, BaseItemType.LIST_CONTENT)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.IMAGE_URL,imgUrl)
                    .build();
            entities.add(entity);
        }
        return entities;
    }
}
