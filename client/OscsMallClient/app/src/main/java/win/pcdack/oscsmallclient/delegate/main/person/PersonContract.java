package win.pcdack.oscsmallclient.delegate.main.person;

import win.pcdack.creamsoda_core.base.BasePresenter;
import win.pcdack.creamsoda_core.base.BaseView;

/**
 * Created by pcdack on 17-11-22.
 *
 */

public class PersonContract {
    interface View extends BaseView{
        void setImageViewBase64(String url);
        void setUserName(String userName);
    }
    interface Presenter extends BasePresenter{

    }
}
