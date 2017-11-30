package win.pcdack.oscsmallclient.delegate.detail.goods;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.base.BaseView;

/**
 * Created by pcdack on 17-11-26.
 *
 */

class GoodsDetailContract {
    interface View extends BaseView{
        void initBanner(ArrayList<String> arrayList);
        void initRichText(String content);
        void initGoodsInfo(GoodsInfoBean bean);
        void setCircleTextViewNum(int count);

    }
    interface Presenter{
        void initData(int productId);
        void addIntoCart();
        void unSubscribe();
    }
}
