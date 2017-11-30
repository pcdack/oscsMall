package win.pcdack.creamsoda_core.util.callback;

import android.support.annotation.Nullable;

/**
 * Created by pcdack on 17-11-1.
 *
 */

public interface IGlobalCallback<T> {
    void executeCallback(@Nullable T args);
}
