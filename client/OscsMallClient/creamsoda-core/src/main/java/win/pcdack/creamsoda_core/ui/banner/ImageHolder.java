package win.pcdack.creamsoda_core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by pcdack on 17-10-21.
 *
 */

public class ImageHolder implements Holder<String>{
    private AppCompatImageView imageView=null;
    private static final RequestOptions OPTIONS=new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();
    @Override
    public View createView(Context context) {
        imageView=new AppCompatImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context)
                .load(data)
                .apply(OPTIONS)
                .into(imageView);
    }
}
