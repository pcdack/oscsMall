package com.pcdack.oscsmall.controller.backend;

import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by pcdack on 17-9-9.
 *
 */
@Controller
@RequestMapping("/manage/user")
public class UserManagerController {

   @Autowired
   private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response=iUserService.login(username,password);
        if (response.isSuccess()) {
            User user=response.getData();
            if (user.getRole()== Const.ROLE.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员无法登录");
            }
        }
        return response;
    }
}
