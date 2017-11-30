package com.pcdack.oscsmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.Shipping;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by pcdack on 17-9-14.
 *
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession httpSession, Shipping shipping){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iShippingService.add(currentUser.getId(),shipping);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession httpSession,Integer shippingId){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iShippingService.del(currentUser.getId(),shippingId);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession httpSession,Shipping shipping){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iShippingService.update(currentUser.getId(),shipping);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(HttpSession httpSession,Integer shippingId){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iShippingService.select(currentUser.getId(),shippingId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession httpSession,
                                         @RequestParam(value = "pageSize",defaultValue ="10")Integer pageSize,
                                         @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iShippingService.list(currentUser.getId(),pageNum,pageSize);
    }

}
