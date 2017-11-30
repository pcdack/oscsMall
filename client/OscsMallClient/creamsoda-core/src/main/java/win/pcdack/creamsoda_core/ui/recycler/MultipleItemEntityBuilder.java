package win.pcdack.creamsoda_core.ui.recycler;

import java.util.LinkedHashMap;

/**
 * Created by pcdack on 17-10-21.
 *
 */

public class MultipleItemEntityBuilder {
    private static final LinkedHashMap<Object,Object> FIELDS=new LinkedHashMap<>();

    public MultipleItemEntityBuilder() {
        FIELDS.clear();
    }
    public final MultipleItemEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }
    public final MultipleItemEntityBuilder setField(Object key, Object value){
        FIELDS.put(key,value);
        return this;
    }
    public final MultipleItemEntityBuilder setFields(LinkedHashMap<?,?> fields){
        FIELDS.putAll(fields);
        return this;
    }
    public final MultipleItemEntity build(){
        return new MultipleItemEntity(FIELDS);
    }

}
