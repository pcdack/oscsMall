package com.pcdack.oscsmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IOrderService;
import com.pcdack.oscsmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by pcdack on 17-9-17.
 *
 */
@RequestMapping("/manage/order")
@Controller
public class OrderManagerController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession httpSession,
                                         @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (iUserService.checkAdmin(currentUser).isSuccess()) {
            return iOrderService.manageList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession httpSession,Long orderNo){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (iUserService.checkAdmin(currentUser).isSuccess()) {
            return iOrderService.manageDetail(orderNo);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> search(HttpSession httpSession, Long orderNo,
                                           @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                           @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (iUserService.checkAdmin(currentUser).isSuccess()) {
            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> sendGoods(HttpSession httpSession,Long orderNo){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    ResponseCode.NEED_LOGIN.getMsg());
        }
        if (iUserService.checkAdmin(currentUser).isSuccess()) {
            return iOrderService.manageSendGoods(orderNo);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
