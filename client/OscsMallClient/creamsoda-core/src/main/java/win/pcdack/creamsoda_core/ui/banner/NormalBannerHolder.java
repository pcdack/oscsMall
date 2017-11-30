package win.pcdack.creamsoda_core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by pcdack on 17-10-9.
 *
 */

public class NormalBannerHolder implements Holder<Integer>{
    private AppCompatImageView imageView=null;
    @Override
    public View createView(Context context) {
        imageView=new AppCompatImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setBackgroundResource(data);
    }
}
