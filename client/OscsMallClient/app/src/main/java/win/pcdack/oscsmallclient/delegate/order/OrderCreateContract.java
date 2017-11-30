package win.pcdack.oscsmallclient.delegate.order;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.base.BaseView;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class OrderCreateContract {
    interface View extends BaseView{
        void setShipDetail(MultipleItemEntity entity);
        void setCartDetail(ArrayList<MultipleItemEntity> entities);
        void setAllPrices(int prices);
    }
    interface Presenter{
        void updateShipInfo(int shipId);
        void getShipDetail();
        void getCartDetail();
        void createOrder(int shippingId);
        void unSubscribe();
    }
}
