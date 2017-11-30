package win.pcdack.oscsmallclient.delegate.main.person.ship;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;

/**
 * Created by pcdack on 17-11-23.
 *
 */

public class ShippingContract {
    interface View extends BaseView{

        void showSwipeRefresh();
        void onDataInit(ArrayList<MultipleItemEntity> entities);

        void cancalSwipeRefresh();

        void onDataAdd(ArrayList<MultipleItemEntity> datas);

        void onLoadMoreDataComplete();
        void onLoadMoreDataFail();
        void onDelSuccess(int position);
    }
    interface Presenter extends BasePresenter{
        void getShippingItems(final boolean isRefresh);
        void deleteShipById(int id,int position);
    }
}
