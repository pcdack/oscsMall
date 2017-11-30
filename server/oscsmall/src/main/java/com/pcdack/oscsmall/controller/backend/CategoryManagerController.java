package com.pcdack.oscsmall.controller.backend;

import com.google.common.collect.Maps;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.ICategoryService;
import com.pcdack.oscsmall.service.IFileService;
import com.pcdack.oscsmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

/**
 * Created by pcdack on 17-9-9.
 *
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManagerController {

    @Autowired
    private IUserService iUserService;


    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,
                                      String categoryName,String imageUrl,
                                      @RequestParam(value = "parentId",defaultValue="0") int parentId){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess() && StringUtils.isNoneEmpty(imageUrl)){
//            是管理员
            return iCategoryService.addCategory(categoryName,parentId,imageUrl);
        }else if (response.isSuccess()){
            return iCategoryService.addCategory(categoryName,parentId);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }

    }


    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,
                                          String categoryName)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }
    @RequestMapping(value = "set_category_image.do")
    @ResponseBody
    public ServerResponse setCategoryImage(HttpSession session,Integer categoryId,
                                          String imageUrl)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            return iCategoryService.updateCategoryImage(categoryId,imageUrl);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }

    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            //查询子节点的category信息,并且不递归,保持平级
            return iCategoryService.getChildrenCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }
    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                             @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),
                    "请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            //查询子节点的category信息,并且不递归,保持平级
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }
}
