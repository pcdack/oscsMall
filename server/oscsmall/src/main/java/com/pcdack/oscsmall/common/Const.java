package com.pcdack.oscsmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by pcdack on 17-9-6.
 *
 */
public class Const {
    public static final String CURRENT_USER="currentUser";
    public static final String USERNAME="username";
    public static final String EMAIL="email";

    // TODO: 17-11-6 VIP用户之类的 
    public interface ROLE{
        int ROLE_CUSTOM=0;//普通用户
        int ROLE_ADMIN=1; //超级用户
    }

    // TODO: 17-11-6 升序
    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price_desc","price_asc");
    }
    public interface CategoryConst{
        int DefaultCategory=0;
    }
    public interface Index{
        int MaxItemCount=4;
    }

    /**
     * 支付宝状态相关
     */
    public interface  AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }
    /**
     * 支付平台
     */
    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝"),
        WECHAT(2,"WZXN");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum ProductType{
        BANNER,
        NORMAL,
        BIG_IMG,
        TEXT,
        TEXT_IMAGE
    }
    /**
     * 购物车
     */
    public interface Cart{
        int CHECK=1;
        int UN_CHECK=0;

        String LIMIT_NUM_FAIL="LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS="LIMIT_NUM_SUCCESS";
    }

    /**
     * 商品状态
     */
    public enum ProductStatusEnum {
        ON_SALE(1,"在线");
        private String msg;
        private int code;

        ProductStatusEnum(int code,String msg) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }


        public int getCode() {
            return code;
        }


    }

    /**
     *商品支付类型
     */
    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付");

        int code;
        String des;

        PaymentTypeEnum(int code, String des) {
            this.code = code;
            this.des = des;
        }

        public int getCode() {
            return code;
        }
        public String getDes() {
            return des;
        }
        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("么有找到对应的枚举");
        }
    }



    /**
     * 交易状态
     */
    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已支付"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单已完成"),
        ORDER_CLOSE(60,"订单关闭");
        private int code;
        private String des;

        OrderStatusEnum(int code, String des) {
            this.code = code;
            this.des = des;
        }

        public int getCode() {
            return code;
        }

        public String getDes() {
            return des;
        }
        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }



    }

}
