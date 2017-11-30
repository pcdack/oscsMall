package win.pcdack.creamsoda_core.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * Created by pcdack on 17-10-22.
 *
 */

public class BaseLookupImpl implements DividerItemDecoration.DividerLookup{
    private final int COLOR;
    private final int SIZE;

    public BaseLookupImpl(int color, int size) {
        this.COLOR = color;
        this.SIZE = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder().size(SIZE).color(COLOR).build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder().size(SIZE).color(COLOR).build();
    }
}
