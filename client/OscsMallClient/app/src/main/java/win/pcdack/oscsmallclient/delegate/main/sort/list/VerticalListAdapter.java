package win.pcdack.oscsmallclient.delegate.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleViewHolder;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.base.BaseItemType;
import win.pcdack.oscsmallclient.delegate.main.sort.SortDelegate;
import win.pcdack.oscsmallclient.delegate.main.sort.content.ContentDelegate;

/**
 * Created by pcdack on 17-10-22.
 *
 */

public class VerticalListAdapter extends MultipleRecyclerAdapter {
    private final SortDelegate SORTDELEGATE;
    private int prePostion=0;
    protected VerticalListAdapter(List<MultipleItemEntity> data, SortDelegate sortDelegate) {
        super(data);
        this.SORTDELEGATE=sortDelegate;
        addItemType(BaseItemType.VERTICAL_LIST_DATA, R.layout.item_vertical_list);

    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        switch (holder.getItemViewType()){
            case BaseItemType.VERTICAL_LIST_DATA:
                final String name=entity.getField(MultipleFields.NAME);
                final boolean isClicked=entity.getField(MultipleFields.TAG);
                final AppCompatTextView nameTv=holder.getView(R.id.sort_list_name);
                final View line=holder.getView(R.id.sort_list_line);
                final View itemView=holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPostion=holder.getAdapterPosition();
                        if (currentPostion!=prePostion){
                            getData().get(prePostion).setField(MultipleFields.TAG,false);
                            notifyItemChanged(prePostion);
                            entity.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPostion);
                            prePostion=currentPostion;
                            final int contentId=getData().get(currentPostion).getField(MultipleFields.ID);
                            show(contentId);
                        }
                    }
                });
                nameTv.setTextColor(ContextCompat.getColor(mContext,R.color.we_chat_black));
                if (!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.app_background));
                }else {
                    line.setVisibility(View.VISIBLE);
                    itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.sort_list_name,name);

                break;
            default:
                break;
        }
    }
    private void show(int contentId){
        final ContentDelegate delegate=ContentDelegate.newInsance(contentId);
        switchContentDelegate(delegate);
    }

    private void switchContentDelegate(ContentDelegate delegate) {
        final CreamSodaDelegate contentDelegate=
                SupportHelper.findFragment(SORTDELEGATE.getChildFragmentManager(),
                ContentDelegate.class);
        if (contentDelegate!=null){
            //第二个参数是是否返回栈
            contentDelegate.getSupportDelegate().replaceFragment(delegate,false);
        }
    }
}
