package com.pcdack.oscsmall.controller.portal;

import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    private static Logger logger= LoggerFactory.getLogger(CategoryController.class);
    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId)
    {
        // TODO: 17-10-23 缺少类型图片
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }

        //查询子节点的category信息,并且不递归,保持平级
        logger.debug("用户已经添加");
        return iCategoryService.getChildrenCategory(categoryId);
    }
    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                             @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}