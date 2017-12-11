package win.pcdack.oscsmallclient.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by pcdack on 17-12-11.
 *
 */

public enum CategoryIcons implements Icon {
    icon_beatiful('\ue6de'),
    icon_fastion('\ue608'),
    icon_electronics('\ue68e');

    char character;
    CategoryIcons(char character) {
        this.character=character;
    }
    @Override
    public String key() {
        return this.name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
