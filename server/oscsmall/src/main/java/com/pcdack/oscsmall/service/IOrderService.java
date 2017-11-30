package com.pcdack.oscsmall.service;

import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.vo.OrderVo;

import java.util.Map;

/**
 * Created by pcdack on 17-9-16.
 *
 */
public interface IOrderService {
    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse cancel(Integer id, Long orderNo);

    ServerResponse orderMsgProduct(Integer id);

    ServerResponse getOrderDetail(Integer id, Long orderNo);

    ServerResponse getOrderList(Integer id, Integer pageNum, Integer pageSize);

//    ServerResponse pay(Long orderNo, Integer id, String path);
//
//    ServerResponse aliCallback(Map<String, String> params);

    ServerResponse queryOrderPayStatus(Integer id, Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, Integer pageNum, Integer pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);

    ServerResponse<PageInfo> manageList(Integer pageNum, Integer pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse getNoPayOrderList(Integer id, Integer pageNum, Integer pageSize);

    ServerResponse getNoReceiptList(Integer id, Integer pageNum, Integer pageSize);
}
