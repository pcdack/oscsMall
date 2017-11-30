package win.pcdack.oscsmallclient.delegate.main.person.order;

/**
 * Created by pcdack on 17-10-31.
 *
 */

public enum OrderEnum {
    LIST(0,"list.do"),
    NO_PAY(1,"no_pay_list.do"),
    SHIPPING(2,"no_receipt_list.do");
    private int code;
    private String des;
    OrderEnum(int code, String des) {
        this.code=code;
        this.des=des;
    }
    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
    public static OrderEnum codeOf(int code){
        for(OrderEnum orderStatusEnum : values()){
            if(orderStatusEnum.getCode() == code){
                return orderStatusEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }
}
