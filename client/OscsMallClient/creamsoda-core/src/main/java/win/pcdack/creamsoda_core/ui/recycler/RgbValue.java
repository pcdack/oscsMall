package win.pcdack.creamsoda_core.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * Created by pcdack on 17-11-12.
 *
 */
@AutoValue
public abstract class RgbValue {
    public abstract int red();
    public abstract int green();
    public abstract int yellow();
    public static RgbValue create(int red,int green,int yellow){
        return new AutoValue_RgbValue(red,green,yellow);
    }
}
