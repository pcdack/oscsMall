package win.pcdack.creamsoda_core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;
import win.pcdack.creamsoda_core.R2;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import win.pcdack.creamsoda_core.R;

/**
 * Created by pcdack on 17-10-20.
 *
 */

public abstract class BaseBottomDelegates extends CreamSodaDelegate implements View.OnClickListener {
    private final ArrayList<BottomTabBean> ITEM_BEEN = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    @BindView(R2.id.delegate_container)
    ContentFrameLayout delegateContainer;
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat bottomBar;
    @ColorInt
    private int color = Color.RED;
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItem(ItemBuilder itemBuilder);

    @ColorInt
    public abstract int setClickColor();

    public abstract int setIndexDelegate();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickColor() != 0)
            color = setClickColor();
        final ItemBuilder itemBuilder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItem(itemBuilder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> entry : ITEMS.entrySet()) {
            final BottomTabBean key = entry.getKey();
            final BottomItemDelegate item = entry.getValue();
            ITEM_BEEN.add(key);
            ITEM_DELEGATES.add(item);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_icon_text_item,bottomBar);
            final LinearLayout item= (LinearLayout) bottomBar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView icon= (IconTextView) item.getChildAt(0);
            final TextView title= (TextView) item.getChildAt(1);
            final BottomTabBean bean=ITEM_BEEN.get(i);
            icon.setText(bean.getIcon());
            title.setText(bean.getTitle());
            if (i == mIndexDelegate){
                icon.setTextColor(color);
                title.setTextColor(color);
            }
        }
        final ISupportFragment[] supportFragments= ITEM_DELEGATES.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.delegate_container,mIndexDelegate,supportFragments);

    }
    private void resetColor(){
        final int conut=bottomBar.getChildCount();
        for (int i = 0; i < conut; i++) {
            final LinearLayout childView= (LinearLayout) bottomBar.getChildAt(i);
            final IconTextView icon= (IconTextView) childView.getChildAt(0);
            final TextView title= (TextView) childView.getChildAt(1);
            icon.setTextColor(Color.GRAY);
            title.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag= (int) v.getTag();
        resetColor();
        final LinearLayout childView= (LinearLayout) v;
        final IconTextView icon= (IconTextView) childView.getChildAt(0);
        final TextView title= (TextView) childView.getChildAt(1);
        icon.setTextColor(color);
        title.setTextColor(color);
        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate=tag;
    }
}
