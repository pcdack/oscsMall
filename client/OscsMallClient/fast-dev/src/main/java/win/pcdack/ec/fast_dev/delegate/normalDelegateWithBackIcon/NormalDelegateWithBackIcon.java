package win.pcdack.ec.fast_dev.delegate.normalDelegateWithBackIcon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.ec.fast_ec.R;
import win.pcdack.ec.fast_ec.R2;

/**
 * Created by pcdack on 17-11-23.
 *
 */

public abstract class NormalDelegateWithBackIcon extends CreamSodaDelegate {
    protected abstract String titleName();
    protected abstract int barColor();
    protected abstract void initRecycleView(RecyclerView recyclerView);

    @BindView(R2.id.my_list)
    RecyclerView myList;
    @BindView(R2.id.toolbar_title)
    TextView titleTV;
    @BindView(R2.id.m_toolbar)
    Toolbar toolbar;


    @Override
    public Object setLayout() {
        return R.layout.delegate_with_backicon;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecycleView(myList);
        if (!TextUtils.isEmpty(titleName())){
            titleTV.setText(titleName());
        }
        if (barColor()!=0)
            toolbar.setBackgroundColor(barColor());
    }
    protected RecyclerView getRecyclerView(){
        if (myList!=null)
            return myList;
        return null;
    }


    @OnClick(R2.id.back_icon)
    public void onViewClicked() {
        getSupportDelegate().pop();
    }
}
