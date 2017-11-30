package win.pcdack.creamsoda_core.base;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public interface BaseView {
    void setLoadingStart();
    void setLoadingFinish();
    void setErrorInfo(int code,String info);
    void setWarningInfo();
}
