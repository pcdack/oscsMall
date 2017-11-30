package win.pcdack.creamsoda_core.ui.recycler;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by pcdack on 17-10-21.
 *
 */

public abstract class DataCoverter {
    protected final ArrayList<MultipleItemEntity> ENTITIES=new ArrayList<>();
    private String mJsonData=null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataCoverter setJsonData(String mJsonData){
        this.mJsonData=mJsonData;
        return this;
    }
    protected String getJsonData(){
        if (TextUtils.isEmpty(mJsonData)){
            throw new RuntimeException("json data is null");
        }
        return mJsonData;
    }
    public void clearData(){
        ENTITIES.clear();
    }

}
