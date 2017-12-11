package win.pcdack.oscsmallclient.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.test.sub.SubCategoryItem;
import win.pcdack.oscsmallclient.test.sub.SubProductItem;

/**
 * Created by pcdack on 17-12-7.
 *
 */

public class TestDataCovnter extends DataCoverter{

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        ArrayList<MultipleItemEntity> multipleItemEntities=new ArrayList<>();
        JSONObject jsonObject= JSON.parseObject(getJsonData());
        //category信息
        JSONArray categoryVos=jsonObject.getJSONArray("categoryVos");
        final int size=categoryVos.size();
        MultipleItemEntity entity;
        entity=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, NewIndexItemType.CATEGORY)
                .setField(NewIndexFields.NAME,"Categories")
                .setField(NewIndexFields.ID,0)
                .setField(NewIndexFields.IMAGE,null)
                .build();
        multipleItemEntities.add(entity);
        ArrayList<SubCategoryItem> subCategoryItems=new ArrayList<>();
        for (int i = 0; i < size; i++) {
            JSONObject object=categoryVos.getJSONObject(i);
            int id=object.getInteger("id");
            String name=object.getString("name");
            String image=object.getString("image");
            StringBuilder childNames=new StringBuilder();
            JSONArray subCategory=object.getJSONArray("childVos");
            int childSize=subCategory.size();
            for (int j = 0; j < childSize; j++) {
                JSONObject subObject=subCategory.getJSONObject(j);
                String childName=subObject.getString("name");
                childNames.append(childName).append(",");
            }
            SubCategoryItem item=new SubCategoryItem();
            item.setTitle(name);
            item.setImage(image);
            item.setSubTitle(childNames.toString());
            subCategoryItems.add(item);
        }
        entity=MultipleItemEntity.builder()
                .setField(MultipleFields.ITEM_TYPE, NewIndexItemType.SUB_ITEM)
                .setField(NewIndexFields.SUB_CATEGORY,subCategoryItems)
                .setField(NewIndexFields.CATEGORY_PRODUCT_FLAG,true)
                .build();
        multipleItemEntities.add(entity);
        //category With product 信息
        JSONArray categoryWithProductVos=jsonObject.getJSONArray("categoryWithProductVos");
        final int categoryWithProductSize=categoryWithProductVos.size();
        for (int i = 0; i < categoryWithProductSize; i++) {
            JSONObject categoryWithProductObject=categoryWithProductVos.getJSONObject(i);
            final int categoryId=categoryWithProductObject.getInteger("id");
            final String name=categoryWithProductObject.getString("name");
            final String categoryImage=categoryWithProductObject.getString("categoryImg");
            entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, NewIndexItemType.CATEGORY)
                    .setField(NewIndexFields.NAME,name)
                    .setField(NewIndexFields.ID,categoryId)
                    .setField(NewIndexFields.IMAGE,categoryImage)
                    .build();
            multipleItemEntities.add(entity);
            ArrayList<SubProductItem> productItems=new ArrayList<>();
            JSONArray productItemJSONArray=categoryWithProductObject.getJSONArray("productListVos");
            final int productItemJSONArraySize=productItemJSONArray.size();
            SubProductItem subProductItem;
            for (int j = 0; j < productItemJSONArraySize; j++) {
                JSONObject object=productItemJSONArray.getJSONObject(j);
                final int id=object. getInteger("id");
                final String productName=object.getString("name");
                final String productSubtitle=object.getString("subtitle");
                final String productMainImage=object.getString("main_image");
                final int productPrice=object.getInteger("price");
                subProductItem=new SubProductItem();
                subProductItem.setName(productName);
                subProductItem.setSubTitle(productSubtitle);
                subProductItem.setImage(productMainImage);
                subProductItem.setId(id);
                subProductItem.setPrice(String.valueOf(productPrice));
                productItems.add(subProductItem);
            }
            if (productItems.size()>0) {
                entity = MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE, NewIndexItemType.SUB_ITEM)
                        .setField(NewIndexFields.PRODUCTS, productItems)
                        .setField(NewIndexFields.CATEGORY_PRODUCT_FLAG, false)
                        .build();
                multipleItemEntities.add(entity);
            }

        }
        return multipleItemEntities;
    }
}
;