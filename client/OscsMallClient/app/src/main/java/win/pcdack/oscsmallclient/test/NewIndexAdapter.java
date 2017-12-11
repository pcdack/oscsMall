package win.pcdack.oscsmallclient.test;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.detail.goods.GoodsDetailDelegate;
import win.pcdack.oscsmallclient.delegate.main.sort.SortDelegate;
import win.pcdack.oscsmallclient.test.sub.SubCategoryItem;
import win.pcdack.oscsmallclient.test.sub.SubRvAdapter;
import win.pcdack.oscsmallclient.test.sub.SubDataCoventer;
import win.pcdack.oscsmallclient.test.sub.SubFields;
import win.pcdack.oscsmallclient.test.sub.SubProductItem;

/**
 * Created by pcdack on 17-12-7.
 *
 */

public class NewIndexAdapter extends MultipleRecyclerAdapter {
    private CreamSodaDelegate delegate;
    protected NewIndexAdapter(List<MultipleItemEntity> data, CreamSodaDelegate delegate) {
        super(data);
        this.delegate=delegate;
    }

    @Override
    protected void init() {
        addItemType(NewIndexItemType.CATEGORY, R.layout.item_category);
        addItemType(NewIndexItemType.SUB_ITEM,R.layout.item_rv);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

        switch (holder.getItemViewType()){
            case NewIndexItemType.CATEGORY:
                final String categoryName=entity.getField(NewIndexFields.NAME);
                final String image=entity.getField(NewIndexFields.IMAGE);
                final int id=entity.getField(NewIndexFields.ID);
                holder.setText(R.id.category_text,categoryName);
                ImageView categoryImageView=holder.getView(R.id.category_image);
                if (TextUtils.isEmpty(image))
                    categoryImageView.setVisibility(View.GONE);
                break;
            case NewIndexItemType.SUB_ITEM:
                RecyclerView recyclerView;
                recyclerView = holder.getView(R.id.my_item_rv);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
                final boolean flag=entity.getField(NewIndexFields.CATEGORY_PRODUCT_FLAG);
                if (flag) {
                    ArrayList<SubCategoryItem> subCategoryItems = entity.getField(NewIndexFields.SUB_CATEGORY);
                    entities.addAll(SubDataCoventer.convertChildCategory(subCategoryItems));
                }else{
                    ArrayList<SubProductItem> subProductItems=entity.getField(NewIndexFields.PRODUCTS);
                    entities.addAll(SubDataCoventer.convertSubProduct(subProductItems));
                }
                SubRvAdapter adapter=new SubRvAdapter(entities);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        MultipleItemEntity entity1;
                        if (!flag) {
                            entity1= entities.get(position);
                            int id=entity1.getField(SubFields.ID);
                            startProductDetailDelegate(id);
                        }else {
                            startSortDelegate();
                        }
                    }
                });
                break;
        }
    }

    private void startSortDelegate() {
        delegate.getSupportDelegate().start(new SortDelegate());
    }

    private void startProductDetailDelegate(int productId) {
        delegate.getSupportDelegate().start(GoodsDetailDelegate.create(productId));
    }

}
