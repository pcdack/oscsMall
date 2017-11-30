package com.pcdack.oscsmall.controller.portal;

import com.google.common.collect.Maps;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

/**
 * Created by pcdack on 17-9-16.
 *
 */
@Controller
@RequestMapping("/order/")
public class OrderController {
    private static Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("create.do")
    @ResponseBody
    public ServerResponse create(HttpSession httpSession, Integer shippingId){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }

        return iOrderService.createOrder(currentUser.getId(),shippingId);
//        return iOrderService.createOrder(21,shippingId);
    }

    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpSession httpSession,Long orderNo){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iOrderService.cancel(currentUser.getId(),orderNo);
    }

    //获得订单的商品信息
    @RequestMapping("order_msg_product.do")
    @ResponseBody
    public ServerResponse orderMsgProduct(HttpSession httpSession){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iOrderService.orderMsgProduct(currentUser.getId());
//        return iOrderService.orderMsgProduct(21);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession httpSession,Long orderNo){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iOrderService.getOrderDetail(currentUser.getId(),orderNo);
//        return iOrderService.getOrderDetail(21,orderNo);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession httpSession,
                               @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iOrderService.getOrderList(currentUser.getId(),pageNum,pageSize);
    }

    @RequestMapping("no_pay_list.do")
    @ResponseBody
    public ServerResponse noPayList(HttpSession httpSession,
                                    @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iOrderService.getNoPayOrderList(currentUser.getId(),pageNum,pageSize);
    }
    @RequestMapping("no_receipt_list.do")
    @ResponseBody
    public ServerResponse noReceiptList(HttpSession httpSession,
                                    @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iOrderService.getNoReceiptList(currentUser.getId(),pageNum,pageSize);
    }

//    /**
//     * 支付宝业务对接
//     */
//    @RequestMapping("pay.do")
//    @ResponseBody
//    public ServerResponse pay(HttpSession httpSession, Long orderNo,
//                              HttpServletRequest httpServletRequest){
//        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
//        if (currentUser == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
//        }
//        String path=httpServletRequest.getSession().getServletContext().getRealPath("upload");
//        return iOrderService.pay(orderNo,currentUser.getId(),path);
//    }
//    //支付宝各种回调
//    @RequestMapping("alipay_callback.do")
//    @ResponseBody
//    public Object alipayCallback(HttpServletRequest request) {
//        Map<String, String> params = Maps.newHashMap();
//
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//
//                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//            }
//            params.put(name, valueStr);
//        }
//        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());
//
//        //非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.
//        params.remove("sign_type");
//        try {
//            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
//            //todo 各种数据的验证
//            if (!alipayRSACheckedV2) {
//                return ServerResponse.createByErrorMessage("非法请求,验证不通过");
//            }
//        } catch (AlipayApiException e) {
//            logger.error("支付宝验证回调异常", e);
//        }
//        ServerResponse serverResponse = iOrderService.aliCallback(params);
//        if (serverResponse.isSuccess()) {
//            return Const.AlipayCallback.RESPONSE_SUCCESS;
//        }
//        return Const.AlipayCallback.RESPONSE_FAILED;
//    }
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }

        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(),orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }
}
