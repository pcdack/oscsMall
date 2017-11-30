package win.pcdack.creamsoda_core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by pcdack on 17-10-21.
 *
 */

public class MultipleViewHolder extends BaseViewHolder{
    public MultipleViewHolder(View view) {
        super(view);
    }
    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
