package win.pcdack.creamsoda_core.net.callback;

/**
 * Created by pcdack on 17-10-7.
 * desc:请求失败
 */

public interface IError {
    void onError(int errorCode, String errorMsg);
}
