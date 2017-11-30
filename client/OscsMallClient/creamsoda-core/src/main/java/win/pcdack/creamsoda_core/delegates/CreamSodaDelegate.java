package win.pcdack.creamsoda_core.delegates;

/**
 * Created by pcdack on 17-11-10.
 *
 */

public abstract class CreamSodaDelegate extends PermissionCheckerDelegate{
    @SuppressWarnings("unchecked")
    public <T extends CreamSodaDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
