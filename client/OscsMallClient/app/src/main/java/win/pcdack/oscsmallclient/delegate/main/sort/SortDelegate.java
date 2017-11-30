package win.pcdack.oscsmallclient.delegate.main.sort;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import win.pcdack.creamsoda_core.delegates.bottom.BottomItemDelegate;
import win.pcdack.oscsmallclient.GlobalVal;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.main.sort.content.ContentDelegate;
import win.pcdack.oscsmallclient.delegate.main.sort.list.VericalListDelegate;

/**
 * Created by pcdack on 17-11-18.
 *
 */

public class SortDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VericalListDelegate verticalListDelegate=new VericalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container,verticalListDelegate);
        getSupportDelegate().loadRootFragment(R.id.content_container, ContentDelegate.newInsance(GlobalVal.DEFAULT_CATEGORY));
    }
}
