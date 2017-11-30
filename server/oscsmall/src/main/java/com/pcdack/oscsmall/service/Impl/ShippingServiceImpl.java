package com.pcdack.oscsmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.dao.ShippingMapper;
import com.pcdack.oscsmall.pojo.Shipping;
import com.pcdack.oscsmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pcdack on 17-9-14.
 *
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCode=shippingMapper.insert(shipping);
        if (rowCode > 0){
//            Map result= Maps.newHashMap();
//            shipping=shippingMapper.s
//            result.put("shippingId",shipping.getId().toString());
            return ServerResponse.createBySuccess("新建地址成功");
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        int rowCode=shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (rowCode > 0){
            return ServerResponse.createBySuccess("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCode=shippingMapper.updateByShipping(shipping);
        if (rowCode> 0){
            return ServerResponse.createBySuccess("更新成功");
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse select(Integer id, Integer shippingId) {
        Shipping shipping=shippingMapper.selectShippingByUserIdAndShippingId(id, shippingId);
        if (shipping != null){
            return ServerResponse.createBySuccess("选择地址成功",shipping);
        }
        return ServerResponse.createByErrorMessage("无法查询到该地址");
    }

    @Override
    public ServerResponse<PageInfo> list(Integer id,
                                         Integer pageNum,
                                         Integer pageSize) {
        if (id == null || pageNum ==null || pageSize == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),
                    ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList=shippingMapper.selectByUserId(id);
        PageInfo pageInfo=new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
