package win.pcdack.ec.fast_dev.launcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.banner.NormalBannerHolderCreator;
import win.pcdack.ec.fast_ec.R;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public abstract class NormalScrollLauncherDelegate extends CreamSodaDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> convenientBanner=null;
    public abstract ArrayList<Integer> pics();
    public abstract void onSubItemClick(int position);
    @Override
    public Object setLayout() {
        convenientBanner=new ConvenientBanner<>(getContext());
        return convenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }

    private void initBanner() {
        ArrayList<Integer> PICS=pics();
        if (PICS!=null && PICS.size()>0) {
            convenientBanner.setPages(new NormalBannerHolderCreator(),PICS).
                    setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus}).
                    setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).
                    setOnItemClickListener(this).
                    setCanLoop(false);
        }
    }

    @Override
    public void onItemClick(int position) {
        onSubItemClick(position);
    }
}
