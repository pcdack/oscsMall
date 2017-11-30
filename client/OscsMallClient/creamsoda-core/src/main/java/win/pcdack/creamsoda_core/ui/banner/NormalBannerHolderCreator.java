package win.pcdack.creamsoda_core.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by pcdack on 17-10-9.
 *
 */

public class NormalBannerHolderCreator implements CBViewHolderCreator<NormalBannerHolder>{
    @Override
    public NormalBannerHolder createHolder() {
        return new NormalBannerHolder();
    }
}
