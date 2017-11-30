package win.pcdack.oscsmallclient.delegate.main.index;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.creamsoda_core.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created by pcdack on 17-11-12.
 *
 */

public class IndexContract {
    interface View extends BaseView{
        void cancalSwipeRefresh();
        void showSwipeRefresh();
        void onDataInit(ArrayList<MultipleItemEntity> datas);
        void onDataAdd(ArrayList<MultipleItemEntity> datas);
    }
    interface Presenter extends BasePresenter{
        void getIndexItems(final boolean isRefresh);
    }
}
