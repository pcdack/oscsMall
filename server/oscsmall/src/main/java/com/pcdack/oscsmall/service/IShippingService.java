package com.pcdack.oscsmall.service;

import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.Shipping;

/**
 * Created by pcdack on 17-9-14.
 *
 */

public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse del(Integer userId, Integer shippingId);
    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse select(Integer id, Integer shippingId);

    ServerResponse<PageInfo> list(Integer id, Integer pageNum, Integer pageSize);
}
