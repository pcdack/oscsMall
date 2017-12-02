package win.pcdack.oscsmallclient.delegate.search;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.recycler.DataCoverter;
import win.pcdack.creamsoda_core.ui.recycler.MultipleFields;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.util.storage.CreamSodaPreference;

/**
 * Created by pcdack on 17-11-2.
 *
 */

class SearchDataCoventer extends DataCoverter {


    final static String HISTORY_FLAG="history_cache";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String jsonStr =
                CreamSodaPreference.getCustomAppProfile(HISTORY_FLAG);
        if (!jsonStr.equals("")) {
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchType.ITEM_SEARCH)
                        .setField(MultipleFields.ITEM_TYPE, SearchType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT, historyItemText)
                        .build();
                ENTITIES.add(entity);
            }
        }

        return ENTITIES;
    }
}
