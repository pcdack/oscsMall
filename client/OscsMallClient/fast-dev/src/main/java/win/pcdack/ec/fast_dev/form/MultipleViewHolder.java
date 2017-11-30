package win.pcdack.ec.fast_dev.form;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class MultipleViewHolder extends  BaseViewHolder{
    public MultipleViewHolder(View view) {
        super(view);
    }
    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }
}
