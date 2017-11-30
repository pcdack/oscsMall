package win.pcdack.ec.fast_dev.form;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.ec.fast_ec.R;
import win.pcdack.ec.fast_ec.R2;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public abstract class NormalFormDelegate extends CreamSodaDelegate {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    public abstract ArrayList<FormType> hints();
    @Override
    public Object setLayout() {
        return R.layout.delegate_form_type;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRecycleView();
        if (hints()!=null && hints().size()>0) {
            FormAdapter formAdapter = new FormAdapter(R.layout.item_normal_form, hints());
            recyclerView.setAdapter(formAdapter);
        }
    }

    private void initRecycleView() {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
    }
}
