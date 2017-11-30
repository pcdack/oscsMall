package win.pcdack.oscsmallclient.delegate.main.person.ship;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.ItemType;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.delegate.main.person.PersonType;

/**
 * Created by pcdack on 17-10-29.
 *
 */

public class ShippingDataCoventer extends DataCoverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities=new ArrayList<>();
        final JSONArray array= JSON.parseObject(getJsonData())
                .getJSONArray("list");
        final int size=array.size();
        for (int i = 0; i < size; i++) {
            JSONObject object=array.getJSONObject(i);
            final int shippingId=object.getInteger("id");
            final String receiverName=object.getString("receiverName");
            final String receiverPhone=object.getString("receiverPhone");
            final String receiverMobile=object.getString("receiverMobile");
            final String receiverProvince=object.getString("receiverProvince");
            final String receiverCity=object.getString("receiverCity");
            final String receiverDistrict=object.getString("receiverDistrict");
            final String receiverAddress=object.getString("receiverAddress");
            final String receiverZip=object.getString("receiverZip");
            MultipleItemEntity entity= MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, PersonType.SHIPPING_CART)
                    .setField(ShippingFields.NAME,receiverName)
                    .setField(ShippingFields.ID,shippingId)
                    .setField(ShippingFields.PHONE,receiverPhone)
                    .setField(ShippingFields.MOBLIE,receiverMobile)
                    .setField(ShippingFields.PROVINCE,receiverProvince)
                    .setField(ShippingFields.CITY,receiverCity)
                    .setField(ShippingFields.DISTRICT,receiverDistrict)
                    .setField(ShippingFields.ADDRESS,receiverAddress)
                    .setField(ShippingFields.ZIP,receiverZip)
                    .build();
            entities.add(entity);
        }
        return entities;
    }
}
