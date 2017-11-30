package com.pcdack.oscsmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.dao.*;
import com.pcdack.oscsmall.pojo.*;
import com.pcdack.oscsmall.service.IOrderService;
import com.pcdack.oscsmall.util.BigDecimalUtil;
import com.pcdack.oscsmall.util.DateTimeUtil;
import com.pcdack.oscsmall.util.PropertiesUtil;
import com.pcdack.oscsmall.util.TimeUtil;
import com.pcdack.oscsmall.vo.OrderItemVo;
import com.pcdack.oscsmall.vo.OrderProductVo;
import com.pcdack.oscsmall.vo.OrderVo;
import com.pcdack.oscsmall.vo.ShippingVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by pcdack on 17-9-16.
 *
 */
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;

    private Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);



    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
//        获得购物车中的商品
        List<Cart> cartList=cartMapper.selectCartByUserId(userId);
        ServerResponse serverResponse=this.getCartOrderItem(userId,cartList);
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItems= (List<OrderItem>) serverResponse.getData();
        BigDecimal payment=this.getOrderTotalPrice(orderItems);

        //生成订单
        Order order=this.assembleOrder(userId,shippingId,payment);

        if (order == null){
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItems)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //mybatis 批量插入
        orderItemMapper.batchInsert(orderItems);
        //减去销售的数量
        this.reduceProductStock(orderItems);
        //清空购物车
        this.clearShipping(cartList);
        //展示给前端
        OrderVo orderVo=this.assembleOrderVo(order,orderItems);
        return ServerResponse.createBySuccess(orderVo);
    }

    //取消订单
    @Override
    public ServerResponse cancel(Integer id, Long orderNo) {
        Order order=orderMapper.selectOrderByIdandOrderNo(id,orderNo);
        if (order == null){
            return ServerResponse.createByErrorMessage("不存在当前订单");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("已经下单，请联系商家退款");
        }
//        Order updateOrder=new Order();

        order.setUserId(id);
        order.setStatus(Const.OrderStatusEnum.CANCELED.getCode());
        int rowCode=orderMapper.updateByPrimaryKeySelective(order);
        if (rowCode>0){
            return ServerResponse.createBySuccess("取消成功,嘤嘤嘤");
        }
        return ServerResponse.createByErrorMessage("错误");
    }

    @Override
    public ServerResponse   orderMsgProduct(Integer id) {
        OrderProductVo orderProductVo=new OrderProductVo();
        List<Cart> cartList=cartMapper.selectCheckedCartByUserId(id);
        ServerResponse serverResponse=this.getCartOrderItem(id,cartList);
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItems= (List<OrderItem>) serverResponse.getData();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment=new BigDecimal("0");
        for (OrderItem orderItem : orderItems) {
            payment= BigDecimalUtil.add(orderItem.getTotalPrice().doubleValue(),payment.doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return ServerResponse.createBySuccess(orderProductVo);
    }

    @Override
    public ServerResponse getOrderDetail(Integer id, Long orderNo) {
        Order order=orderMapper.selectOrderByIdandOrderNo(id,orderNo);
        if (order != null){
            List<OrderItem> orderItems=orderItemMapper.getByUserIdAndOrderNo(id,orderNo);
            OrderVo orderVo=assembleOrderVo(order,orderItems);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("没有这个订单");
    }



//    @Override
//    public ServerResponse pay(Long orderNo, Integer id, String path) {
//        Map<String,String> resultMap= Maps.newHashMap();
//        Order order=orderMapper.selectOrderByIdandOrderNo(id,orderNo);
//        if (order == null){
//            return ServerResponse.createByErrorMessage("用户没有订单");
//        }
//        resultMap.put("orderNo",String.valueOf(order.getOrderNo()));
////        (必填)商户的订单号
//        String outTradeNo=order.getOrderNo().toString();
////        (必填)订单标题
//        String subject=new StringBuilder().append("mmall扫码支付，订单号:").append(outTradeNo).toString();
//        // (必填) 订单总金额，单位为元，不能超过1亿元
//        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
//        String totalAmount=order.getPayment().toString();
//        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
//        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
//        String undiscountableAmount= "0";
//        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
//        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//        String sellerId="";
//        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
//        String body=new StringBuilder().append("商品一共").append(totalAmount).append("元").toString();
//        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
//        String operatorId = "test_operator_id";
//
//        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
//        String storeId = "test_store_id";
//        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//        ExtendParams extendParams = new ExtendParams();
//        extendParams.setSysServiceProviderId("2088100200300400500");
//
//        // 支付超时，定义为120分钟
//        String timeoutExpress = "30m";
//        List<GoodsDetail> goodsDetails=new ArrayList<>();
//        List<OrderItem> orderItemList=orderItemMapper.getByOrderNoUserId(orderNo,id);
//        for (OrderItem orderItem : orderItemList) {
////            商品价格的单位是分，所以应当要乘以100
//            GoodsDetail goodsDetail=GoodsDetail.newInstance(orderItem.getProductId().toString(),
//                    orderItem.getProductName(),
//                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(),new Double(100).doubleValue()).longValue(),
//                    orderItem.getQuantity());
//            goodsDetails.add(goodsDetail);
//        }
//        // 创建扫码支付请求builder，设置请求参数
//        AlipayTradePrecreateRequestBuilder builder=new AlipayTradePrecreateRequestBuilder()
//                .setSubject(subject)
//                .setTotalAmount(totalAmount)
//                .setOutTradeNo(outTradeNo)
//                .setUndiscountableAmount(undiscountableAmount)
//                .setSellerId(sellerId)
//                .setBody(body)
//                .setOperatorId(operatorId)
//                .setStoreId(storeId)
//                .setExtendParams(extendParams)
//                .setTimeoutExpress(timeoutExpress)
//                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
//                .setGoodsDetailList(goodsDetails);
//        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
//        switch (result.getTradeStatus()) {
//            case SUCCESS:
//                logger.info("支付宝预下单成功: )");
//
//                AlipayTradePrecreateResponse response = result.getResponse();
//                dumpResponse(response);
//
//                File folder = new File(path);
//                if(!folder.exists()){
//                    folder.setWritable(true);
//                    folder.mkdirs();
//                }
//
//                // 需要修改为运行机器上的路径
//                //细节细节细节
//                String qrPath = String.format(path+"/qr-%s.png",response.getOutTradeNo());
//                String qrFileName = String.format("qr-%s.png",response.getOutTradeNo());
//                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);
//
//                File targetFile = new File(path,qrFileName);
//                try {
//                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
//                } catch (IOException e) {
//                    logger.error("上传二维码异常",e);
//                }
//                logger.info("qrPath:" + qrPath);
//                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName();
//                resultMap.put("qrUrl",qrUrl);
//                return ServerResponse.createBySuccess(resultMap);
//            case FAILED:
//                logger.error("支付宝预下单失败!!!");
//                return ServerResponse.createByErrorMessage("支付宝预下单失败!!!");
//
//            case UNKNOWN:
//                logger.error("系统异常，预下单状态未知!!!");
//                return ServerResponse.createByErrorMessage("系统异常，预下单状态未知!!!");
//
//            default:
//                logger.error("不支持的交易状态，交易返回异常!!!");
//                return ServerResponse.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
//        }
//
//
//    }

//    @Override
//    public ServerResponse aliCallback(Map<String, String> params) {
//        Long orderNo = Long.parseLong(params.get("out_trade_no"));
//        String tradeNo = params.get("trade_no");
//        String tradeStatus = params.get("trade_status");
//        Order order = orderMapper.selectByOrderNo(orderNo);
//        if(order == null){
//            return ServerResponse.createByErrorMessage("非快乐慕商城的订单,回调忽略");
//        }
//        if(order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
//            return ServerResponse.createBySuccess("支付宝重复调用");
//        }
//        if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
//            order.setPaymentTime(TimeUtil.strTodate(params.get("gmt_payment")));
//            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
//            logger.info("order状态更新为:"+Const.OrderStatusEnum.PAID.getDes());
//            if (orderMapper.updateByPrimaryKeySelective(order)> 0) {
//                logger.info("更新数据库成功");
//            }else {
//                logger.info("更新数据库失败");
//            }
//        }
//        PayInfo payInfo = new PayInfo();
//        payInfo.setUserId(order.getUserId());
//        payInfo.setOrderNo(order.getOrderNo());
//        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
//        payInfo.setPlatformNumber(tradeNo);
//        payInfo.setPlatformStatus(tradeStatus);
//
//        payInfoMapper.insert(payInfo);
//
//        return ServerResponse.createBySuccess();
//    }

    @Override
    public ServerResponse queryOrderPayStatus(Integer id, Long orderNo) {
        Order order = orderMapper.selectOrderByIdandOrderNo(id,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if(order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();

    }

    /**
     * manage部分
     */
    @Override
    public ServerResponse<PageInfo> manageSearch(Long orderNo,
                                                 Integer pageNum,
                                                 Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Order order=orderMapper.selectByOrderNo(orderNo);
        if (order != null){
            List<OrderItem> orderItems=orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo=assembleOrderVo(order,orderItems);

            PageInfo pageInfo=new PageInfo(Lists.newArrayList(order));
            pageInfo.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByErrorMessage("订单不存在");
    }

    @Override
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        Order order=orderMapper.selectByOrderNo(orderNo);
        if (order!=null) {
            if (order.getStatus() == Const.OrderStatusEnum.PAID.getCode()) {
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
                return ServerResponse.createBySuccess("发货成功");
            }
        }
        return ServerResponse.createByErrorMessage("发货失败");
    }

    @Override
    public ServerResponse<PageInfo> manageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders=orderMapper.selectAllOrder();
        List<OrderVo> orderVos=this.assembleOrderVoList(orders,null);
        PageInfo pageResult=new PageInfo(orders);
        pageResult.setList(orderVos);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        Order order=orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            List<OrderItem> orderItems=orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo=assembleOrderVo(order,orderItems);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByErrorMessage("失败");
    }
    @Override
    public ServerResponse getOrderList(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders=orderMapper.selectByUserId(id);
        List<OrderVo> orderVos=assembleOrderVoList(orders,id);
        PageInfo pageResult = new PageInfo(orders);
        pageResult.setList(orderVos);
        return ServerResponse.createBySuccess(pageResult);
    }
    @Override
    public ServerResponse getNoPayOrderList(Integer id,
                                            Integer pageNum,
                                            Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders=orderMapper.selectByUserId(id);
        List<OrderVo> orderVos=assembleOrderVoList(orders,id, Const.OrderStatusEnum.NO_PAY);
        PageInfo pageResult = new PageInfo(orders);
        pageResult.setList(orderVos);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse getNoReceiptList(Integer id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders=orderMapper.selectByUserId(id);
        List<OrderVo> orderVos=assembleOrderVoList(orders,id, Const.OrderStatusEnum.SHIPPED);
        PageInfo pageResult = new PageInfo(orders);
        pageResult.setList(orderVos);
        return ServerResponse.createBySuccess(pageResult);
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orders, Integer id, Const.OrderStatusEnum shipped) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orders) {
            List<OrderItem> orderVos=Lists.newArrayList();
            if (id == null){
                orderVos = orderItemMapper.getByOrderNo(order.getOrderNo());
            }else {
                orderVos=orderItemMapper.getByOrderNoUserId(order.getOrderNo(),id);
            }
            OrderVo orderVo=assembleOrderVo(order,orderVos);
            if (orderVo.getStatusDesc().equals(shipped.getDes())) {
                orderVoList.add(orderVo);
            }
        }
        return orderVoList;
    }

//    // 简单打印应答
//    private void dumpResponse(AlipayTradePrecreateResponse response) {
//        if (response != null) {
//            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
//            if (StringUtils.isNotEmpty(response.getSubCode())) {
//                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
//                        response.getSubMsg()));
//            }
//            logger.info("body:" + response.getBody());
//        }
//    }

    private List<OrderVo> assembleOrderVoList(List<Order> orders, Integer id) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Order order : orders) {
            List<OrderItem> orderVos=Lists.newArrayList();
            if (id == null){
                orderVos = orderItemMapper.getByOrderNo(order.getOrderNo());
            }else {
                orderVos=orderItemMapper.getByOrderNoUserId(order.getOrderNo(),id);
            }
            OrderVo orderVo=assembleOrderVo(order,orderVos);
            orderVoList.add(orderVo);

        }
        return orderVoList;
    }
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo=new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(TimeUtil.dateTostr(orderItem.getCreateTime()));
        return orderItemVo;
    }


    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItems) {
        OrderVo orderVo=new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getDes());
        orderVo.setPostage(order.getPostage());
        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));
        orderVo.setStatus(order.getStatus());
        orderVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getDes());
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping=shippingMapper.selectByPrimaryKey(order.getShippingId());
        if (shipping != null){
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(assembleShippingVo(shipping));
        }
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        for(OrderItem orderItem : orderItems){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;

    }

    private ShippingVo assembleShippingVo(Shipping shipping) {
        ShippingVo shippingVo=new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    private void clearShipping(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private void reduceProductStock(List<OrderItem> orderItems){
        for (OrderItem orderItem : orderItems) {
            Product product=productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order=new Order();
        long orderNo=this.generateOrderNum();
        order.setOrderNo(orderNo);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());

        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        int rowCount=orderMapper.insert(order);
        if (rowCount > 0){
            return order;
        }
        return null;
    }

    private long generateOrderNum() {
        long currentTime=System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItems) {
        BigDecimal totalPrice=new BigDecimal("0");
        for (OrderItem orderItem : orderItems) {
            totalPrice=BigDecimalUtil.add(orderItem.getTotalPrice().doubleValue(),totalPrice.doubleValue());
        }
        return totalPrice;
    }

    private ServerResponse getCartOrderItem(Integer userId, List<Cart> cartList) {
        List<OrderItem> orderItems= Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //校验购物车的状态是否正确
        for (Cart cartItem : cartList) {
            OrderItem orderItem=new OrderItem();
            Product product=productMapper.selectByPrimaryKey(cartItem.getProductId());
            //校验是否处于在售状态
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()){
                return ServerResponse.createByErrorMessage("商品处于下架状态");
            }
            //校验是否存在库存
            if (cartItem.getQuantity() > product.getStock()){
                return ServerResponse.createByErrorMessage("商品"+product.getName()+"库存数量不足");
            }

            orderItem.setUserId(userId);
            orderItem.setProductName(product.getName());
            orderItem.setProductId(product.getId());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
            orderItems.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItems);
    }

}
