package win.pcdack.oscsmallclient.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by pcdack on 17-12-11.
 *
 */

public class CategoryModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return CategoryIcons.values();
    }
}
