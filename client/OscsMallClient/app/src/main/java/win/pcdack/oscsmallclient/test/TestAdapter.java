package win.pcdack.oscsmallclient.test;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-12-7.
 *
 */

public class TestAdapter extends MultipleRecyclerAdapter {
    protected TestAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    @Override
    protected void init() {
        addItemType(TestItemType.CATEGORY, R.layout.item_category);
        addItemType(TestItemType.SUB_ITEM,R.layout.item_rv);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        switch (holder.getItemViewType()){
            case TestItemType.CATEGORY:
                Logger.d("进入CATEGORY");
                final String categoryName=entity.getField(TestFields.NAME);
                final String image=entity.getField(TestFields.IMAGE);
                final int id=entity.getField(TestFields.ID);
                holder.setText(R.id.category_text,categoryName);
                ImageView categoryImageView=holder.getView(R.id.category_image);
                if (TextUtils.isEmpty(image))
                    categoryImageView.setVisibility(View.GONE);
                break;
            case TestItemType.SUB_ITEM:
                Logger.d("进入");
                RecyclerView recyclerView;
                recyclerView = holder.getView(R.id.my_item_rv);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                ArrayList<MultipleItemEntity> entities = new ArrayList<>();
                boolean flag=entity.getField(TestFields.CATEGORY_PRODUCT_FLAG);
                if (flag) {
                    ArrayList<SubCategoryItem> subCategoryItems = entity.getField(TestFields.SUB_CATEGORY);
                    entities.addAll(SubDataCoventer.convertChildCategory(subCategoryItems));
                }else{
                    ArrayList<SubProductItem> subProductItems=entity.getField(TestFields.PRODUCTS);
                    entities.addAll(SubDataCoventer.convertSubProduct(subProductItems));
                }
                SubCategoryRvAdapter adapter=new SubCategoryRvAdapter(entities);
                recyclerView.setAdapter(adapter);
                break;
        }
    }
}
