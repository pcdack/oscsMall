package com.pcdack.oscsmall.controller.portal;

import com.google.common.collect.Maps;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IFileService;
import com.pcdack.oscsmall.service.IUserService;
import com.pcdack.oscsmall.util.PropertiesUtil;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by pcdack on 17-9-6.
 *
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IFileService iFileService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response=iUserService.login(username,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    @RequestMapping(value = "user_avatar_upload.do")
    @ResponseBody
    public ServerResponse userAvatarUpload(HttpSession session,
                                           @RequestParam(value = "upload_file",required = false)MultipartFile file,
                                           HttpServletRequest request)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        String path=request.getSession().getServletContext().getRealPath("upload");
        String targetFileName=iFileService.uploadUserImage(file,path);
        String url= PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap= Maps.newHashMap();
        //noinspection unchecked
        fileMap.put("uri",targetFileName);
        //noinspection unchecked
        fileMap.put("url",url);
        currentUser.setAvatar(url);
        ServerResponse<User> response=iUserService.updateInformation(currentUser);
        if (response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping(value = "logout.do")
    @ResponseBody
    public ServerResponse<User> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess("登出成功");
    }
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> valid(String str,String type){
        return iUserService.checkValid(str,type);
    }
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser!=null){
            return ServerResponse.createBySuccess(currentUser);
        }
        return ServerResponse.createByErrorMessage("用户没有登录，无法获取当前用户信息");
    }
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, @NotNull String newPassword, String forgetToken){
        if (StringUtils.isEmpty(newPassword))
            return ServerResponse.createByErrorMessage("新密码为空");
        return iUserService.forgetResetPassword(username, newPassword, forgetToken);
    }
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,
                                                String oldPassword,
                                                String newPassword)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorMessage("用户没有登录");
        }
        return iUserService.resetPassword(oldPassword,newPassword,currentUser);
    }
    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User current= (User) session.getAttribute(Const.CURRENT_USER);
        if (current==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(current.getId());
        user.setUsername(current.getUsername());
        ServerResponse<User> response=iUserService.updateInformation(user);
        if (response.isSuccess()){
            response.getData().setUsername(current.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    @RequestMapping(value = "get_information.do")
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，status=10");
        }

        return iUserService.getInformation(currentUser.getId());
    }


}
