package win.pcdack.oscsmallclient.test.sub;

import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.oscsmallclient.test.NewIndexItemType;

/**
 * Created by pcdack on 17-12-9.
 *
 */

public class SubDataCoventer {
    public static List<MultipleItemEntity> convertChildCategory(List<SubCategoryItem> subCategoryItems){
        ArrayList<MultipleItemEntity> multipleItemEntities=new ArrayList<>();
        MultipleItemEntity entity;
        for (SubCategoryItem subCategoryItem : subCategoryItems) {
            final String subName=subCategoryItem.getTitle();
            final String subSubTitle=subCategoryItem.getSubTitle();
            final String subImage=subCategoryItem.getImage();
            entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, NewIndexItemType.CHILD_CATEGORY)
                    .setField(SubFields.NAME,subName)
                    .setField(SubFields.CHILD_CATEGORY,subSubTitle)
                    .setField(SubFields.IMAGE,subImage)
                    .build();
            multipleItemEntities.add(entity);
        }

        return multipleItemEntities;
    }
    public static List<MultipleItemEntity> convertSubProduct(List<SubProductItem> subProductItems){
        ArrayList<MultipleItemEntity> multipleItemEntities=new ArrayList<>();
        MultipleItemEntity entity;
        for (SubProductItem subProductItem : subProductItems) {
            final String subName=subProductItem.getName();
            final String subSubTitle=subProductItem.getSubTitle();
            final String subImg=subProductItem.getImage();
            final String subPrice=subProductItem.getPrice();
            final int subId=subProductItem.getId();

            entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, NewIndexItemType.SUB_PRODUCT)
                    .setField(SubFields.NAME,subName)
                    .setField(SubFields.SUB_TITLE,subSubTitle)
                    .setField(SubFields.PRICE,subPrice)
                    .setField(SubFields.IMAGE,subImg)
                    .setField(SubFields.ID,subId)
                    .build();
            multipleItemEntities.add(entity);
        }
        return multipleItemEntities;
    }
}
