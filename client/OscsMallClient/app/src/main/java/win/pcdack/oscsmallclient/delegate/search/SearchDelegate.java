package win.pcdack.oscsmallclient.delegate.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.util.storage.CreamSodaPreference;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.search.search_goods.SearchGoodsDelegate;

import static win.pcdack.oscsmallclient.delegate.search.SearchDataCoventer.HISTORY_FLAG;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class SearchDelegate extends CreamSodaDelegate {
    @BindView(R.id.search_back_icon)
    IconTextView searchBackIcon;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_rv)
    RecyclerView searchRv;

    private SearchAdapter adapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        searchRv.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataCoventer().convert();
        adapter = new SearchAdapter(data);
        searchRv.setAdapter(adapter);
    }
    private void saveItem(String item) {
        if (!TextUtils.isEmpty(item)) {
            List<String> history;
            final String historyStr =
                    CreamSodaPreference.getCustomAppProfile(HISTORY_FLAG);
            if (TextUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                //noinspection unchecked
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);

            CreamSodaPreference.addCustomAppProfile(HISTORY_FLAG, json);
        }
    }


    @OnClick(R.id.search_icon)
    public void onViewClicked() {
        String searchStr=searchEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(searchStr)) {
            saveItem(searchStr);
            searchEdit.setText("");
            MultipleItemEntity entity=MultipleItemEntity.builder().setField(MultipleFields.TEXT,searchStr).build();
            adapter.addData(entity);
            // TODO: 17-11-2 打开SearchGoodsDelegate
            getSupportDelegate().start(SearchGoodsDelegate.create(searchStr));

        }
    }
    @OnClick(R.id.search_back_icon)
    void onBackClick(){
        getSupportDelegate().pop();
    }
}
