package win.pcdack;

/**
 * Created by pcdack on 17-11-11.
 *
 */

public class GlobalUrlVal {

    private static final String BASE_URL="http://39.108.169.241:8080/oscsmall";
    //user
    private static final String USER_URL=BASE_URL+"/user/";
    public static final String SIGN_IN_URL=USER_URL+"login.do";
    public static final String SIGN_UP_URL=USER_URL+"register.do";
    public static final String GET_INFORMATION=USER_URL+"get_information.do";
    public static final String UPDATE_INFORMATION=USER_URL+"update_information.do";
//    public static final String USER_AVATAR=USER_URL+"user_avatar_upload.do";

    //product
    private static final String PRODUCT=BASE_URL+"/product/";
    public static final String PRODUCT_LIST_URL=PRODUCT+"list.do";
    public static final String PRODUCT_DATAIL=PRODUCT+"detail.do";
    public static final String PRODUCT_SEARCH_BY_KEYWORDS=PRODUCT+"searchByKeyWord.do";
    public static final String PRODUCT_SEARCH=PRODUCT+"search.do";


    //category
    private static final String CATEGORY=BASE_URL+"/category/";
    public static final String CATEGORY_LIST=CATEGORY+"get_category.do";

    //cart
    private static final String CART=BASE_URL+"/cart/";
    public static final String CART_LIST=CART+"list.do";
    public static final String CART_UPDATE=CART+"update.do";
    public static final String CART_ADD=CART+"add.do";
    public static final String CART_SELECT=CART+"select.do";
    public static final String CART_DELETE=CART+"delete_product.do";
    public static final String CART_UN_SELECT=CART+"un_select.do";
    public static final String CART_SELECT_ALL=CART+"select_all.do";
    public static final String CART_UN_SELECT_ALL=CART+"un_select_all.do";

    //ship
    private static final String SHIP=BASE_URL+"/shipping/";
    public static final String SHIP_LIST=SHIP+"list.do";
    public static final String SHIP_SELECT=SHIP+"select.do";
    public static final String SHIP_DEL=SHIP+"del.do";
    public static final String SHIP_ADD=SHIP+"add.do";
    public static final String SHIP_UPDATE=SHIP+"update.do";
    //order
    private static final String ORDER=BASE_URL+"/order/";
    public static final String ORDER_LIST=ORDER+"list.do";
    public static final String UNPAY_ORDER_LIST=ORDER+"no_pay_list.do";
    public static final String NO_RECEIPT_ORDER_LIST=ORDER+"no_receipt_list.do";
    public static final String ORDER_CREATE=ORDER+"create.do";



}
