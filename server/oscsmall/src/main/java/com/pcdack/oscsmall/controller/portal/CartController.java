package com.pcdack.oscsmall.controller.portal;

import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.ICartService;
import com.pcdack.oscsmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by pcdack on 17-9-13.
 * func：购物车模块
 */
@Controller
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iCartService.getList(currentUser.getId());
//        return iCartService.getList(21);
    }

    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> add(HttpSession httpSession,Integer count,Integer productId){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
//        return iCartService.add(21,count,productId);
        return iCartService.add(currentUser.getId(),count,productId);
    }

    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> update(HttpSession session,Integer count,Integer productId){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iCartService.update(currentUser.getId(),count,productId);
//        return iCartService.update(21,count,productId);
    }


    @RequestMapping(value = "delete_product.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(HttpSession httpSession,String productIds){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
//        return iCartService.deleteProduct(21,productIds);
        return iCartService.deleteProduct(currentUser.getId(),productIds);
    }

    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpSession httpSession){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
//        return iCartService.selectOrUnSelect(21,null, Const.Cart.CHECK);
        return iCartService.selectOrUnSelect(currentUser.getId(),null,Const.Cart.CHECK);
    }

    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpSession httpSession){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
//        return iCartService.selectOrUnSelect(21,null,Const.Cart.UN_CHECK);
        return iCartService.selectOrUnSelect(currentUser.getId(),null,Const.Cart.UN_CHECK);
    }

    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> select(HttpSession session,Integer productId){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iCartService.selectOrUnSelect(currentUser.getId(),productId,Const.Cart.CHECK);
//        return iCartService.selectOrUnSelect(21,productId,Const.Cart.CHECK);
    }
    @RequestMapping(value = "un_select.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartVo> unSelect(HttpSession session,Integer productId){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return iCartService.selectOrUnSelect(currentUser.getId(),productId,Const.Cart.UN_CHECK);
//        return iCartService.selectOrUnSelect(21,productId,Const.Cart.UN_CHECK);
    }

    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession httpSession){
        User currentUser= (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(currentUser.getId());
    }

}
