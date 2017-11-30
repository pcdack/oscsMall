package win.pcdack.oscsmallclient.delegate.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.ItemType;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;

/**
 * Created by pcdack on 17-10-27.
 *
 */

public class CartDataCoventer extends DataCoverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        JSONObject jsonObject= JSON.parseObject(getJsonData());
        JSONArray array=jsonObject.getJSONArray("cartProductVoList");
        final int size=array.size();
        for (int i = 0; i < size; i++) {
            JSONObject object=array.getJSONObject(i);
            final String productName=object.getString("productName");
            final String productSubtitle=object.getString("productSubtitle");
            final String productMainImage=object.getString("productMainImage");
            final int productPrice=object.getInteger("productPrice");
            final int productTotalPrice=object.getInteger("productTotalPrice");
            final int productChecked=object.getInteger("productChecked");
            final int quantity=object.getInteger("quantity");
            final int id=object.getInteger("productId");
            final int productStock=object.getInteger("productStock");
            MultipleItemEntity entity= MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, CartItemType.LIST_CART)
                    .setField(MultipleFields.NAME,productName)
                    .setField(MultipleFields.TEXT,productSubtitle)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.QUANTITY,quantity)
                    .setField(MultipleFields.CHECKED,productChecked==1)
                    .setField(MultipleFields.IMAGE_URL,productMainImage)
                    .setField(MultipleFields.POSTION,i)
                    .build();
            entities.add(entity);
        }
        return entities;
    }
}
