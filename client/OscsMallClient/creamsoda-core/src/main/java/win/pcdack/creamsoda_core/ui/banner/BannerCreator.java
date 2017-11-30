package win.pcdack.creamsoda_core.ui.banner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.R;

/**
 * Created by pcdack on 17-10-21.
 *
 */

public class BannerCreator {
    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners,
                                  OnItemClickListener listener){
        convenientBanner.setPages(new HolderCreator(),banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .setOnItemClickListener(listener)
                .startTurning(3000)

                .setCanLoop(true);

    }
}
