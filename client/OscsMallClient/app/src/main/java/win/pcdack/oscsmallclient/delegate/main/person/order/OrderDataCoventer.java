package win.pcdack.oscsmallclient.delegate.main.person.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.delegate.main.person.PersonType;
import win.pcdack.oscsmallclient.delegate.main.person.ship.ShippingVo;

/**
 * Created by pcdack on 17-10-29.
 *
 */

class OrderDataCoventer extends DataCoverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> entities=new ArrayList<>();

        final JSONArray jsonArray= JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("list");
        final int size=jsonArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject object=jsonArray.getJSONObject(i);
            final String paymentTypeDesc=object.getString("paymentTypeDesc");
            final String statusDesc=object.getString("statusDesc");
            final int payment=object.getInteger("payment");
            final String image_host=object.getString("imageHost");

            ShippingVo shippingVo=new ShippingVo();
            JSONObject shipping=object.getJSONObject("shippingVo");
            shippingVo.setReceiverProvince(shipping.getString("receiverProvince"));
            shippingVo.setReceiverCity(shipping.getString("receiverCity"));
            shippingVo.setReceiverDistrict(shipping.getString("receiverDistrict"));
            shippingVo.setReceiverAddress(shipping.getString("receiverAddress"));

            ArrayList<OrderItemVo> vos=new ArrayList<>();
            final JSONArray array=object.getJSONArray("orderItemVoList");
            final int vosSize=array.size();
            for (int j = 0; j < vosSize; j++) {
                JSONObject vosObject=array.getJSONObject(j);
                OrderItemVo vo=new OrderItemVo();
                final String productName=vosObject.getString("productName");
                vo.setOrderNo(vosObject.getLong("orderNo"));
                vo.setProductId(vosObject.getInteger("productId"));
                vo.setProductName(productName);
                vo.setProductImage(vosObject.getString("productImage"));
                vo.setCurrentUnitPrice(vosObject.getInteger("currentUnitPrice"));
                vo.setQuantity(vosObject.getInteger("quantity"));
                vo.setTotalPrice(vosObject.getInteger("totalPrice"));
                vos.add(vo);
            }
            final MultipleItemEntity entity= MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, PersonType.ORDER_CART)
                    .setField(OrderField.PAYMENT_TYPE_DESC,paymentTypeDesc)
                    .setField(OrderField.STATUSDESC,statusDesc)
                    .setField(OrderField.PAYMENT,payment)
                    .setField(OrderField.ORDER_VOS,vos)
                    .setField(OrderField.POSTION,shippingVo)
                    .setField(OrderField.IMAGE_HOST,image_host)
                    .build();
            entities.add(entity);
        }

        return entities;
    }
}
