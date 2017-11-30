package win.pcdack.creamsoda_core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by pcdack on 17-10-20.
 *
 */

//简单工厂模式
public class ItemBuilder {
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS=new LinkedHashMap<>();
    static ItemBuilder builder(){
        return new ItemBuilder();
    }
    public final ItemBuilder setItem(BottomTabBean bean, BottomItemDelegate delegate){
        ITEMS.put(bean,delegate);
        return this;
    }
    public final ItemBuilder setItems(LinkedHashMap<BottomTabBean,BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }
    public final LinkedHashMap<BottomTabBean,BottomItemDelegate> build(){
        return ITEMS;
    }

}
