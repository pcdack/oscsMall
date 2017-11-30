package win.pcdack.oscsmallclient.delegate.main.cart;



import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;

/**
 * Created by pcdack on 17-11-19.
 *
 */

public class CartContract {
    interface View extends BaseView{
        //外层View
        void setAllSelectStatus(boolean turnOn);
        void setAllPrices(int num);
        //Recycler View
        void setCartItems(List<MultipleItemEntity> entities);
        void updateCartItems(List<MultipleItemEntity> entities);

    }
    interface Presenter extends BasePresenter{
        //外层Presenter
        void deleteItems();
        boolean isAllSelect();
        //Recycler层Presenter
        void recyclerItemAddOnClick(int position);
        void recyclerItemLesOnClick(int position);
        void recyclerItemSelectIconClick(int position);
        void recyclerItemSelectAll();
        void recyclerItemUnSelectAll();
    }
}
