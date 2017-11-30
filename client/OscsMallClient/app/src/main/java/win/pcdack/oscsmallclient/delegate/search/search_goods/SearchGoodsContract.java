package win.pcdack.oscsmallclient.delegate.search.search_goods;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.base.BaseView;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class SearchGoodsContract {
    interface View extends BaseView{
        void setData(ArrayList<MultipleItemEntity> entities);

    }
    interface Presenter {
        void initSearchByKeyWord(String keyword);
        void initSearchByKeyWordAndCategory(String keyword,int categoryId);
        void unSubscribe();
    }
}
