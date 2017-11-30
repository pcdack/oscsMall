package win.pcdack.oscsmallclient.delegate.main.index;

/**
 * Created by pcdack on 17-11-15.
 * func:首页展示类型的Enum
 */

public enum IndexType {
    BANNER(0),
    NORMAL(1),
    BIG_IMG(2),
    TEXT(3);

    IndexType(int code) {
        this.code=code;
    }
    int code;
    public int getCode() {
        return code;
    }
}
