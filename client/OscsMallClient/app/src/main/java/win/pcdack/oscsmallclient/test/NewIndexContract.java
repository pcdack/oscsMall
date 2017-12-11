package win.pcdack.oscsmallclient.test;

import java.util.ArrayList;
import java.util.List;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;

/**
 * Created by pcdack on 17-12-8.
 *
 */

public class NewIndexContract {
    interface View extends BaseView{
        void setData(ArrayList<MultipleItemEntity> entities);
    }
    interface Presenter extends BasePresenter{

    }
}
