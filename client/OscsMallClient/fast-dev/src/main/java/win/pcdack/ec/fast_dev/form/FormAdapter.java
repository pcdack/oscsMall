package win.pcdack.ec.fast_dev.form;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import win.pcdack.ec.fast_ec.R;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class FormAdapter extends BaseQuickAdapter<FormType,BaseViewHolder>{
    public FormAdapter(@LayoutRes int layoutResId, @Nullable List<FormType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FormType item) {
        TextInputEditText inputEditText=helper.getView(R.id.my_edit);
        inputEditText.setHint(item.getHint());
    }
}
