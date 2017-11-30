package win.pcdack.oscsmallclient.delegate.main.discover;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import win.pcdack.creamsoda_core.delegates.bottom.BottomItemDelegate;
import win.pcdack.creamsoda_core.webview.WebViewDelegateImpl;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-10-23.
 *
 */

public class DiscoverDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebViewDelegateImpl webViewDelegate= WebViewDelegateImpl.create("index.html");
        webViewDelegate.setmTopDelegrate(this.getParentDelegate());
        getSupportDelegate().loadRootFragment(R.id.discover_container,webViewDelegate);
    }
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
