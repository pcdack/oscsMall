package com.pcdack.oscsmall.controller.backend;

import com.google.common.collect.Maps;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.Product;
import com.pcdack.oscsmall.pojo.User;
import com.pcdack.oscsmall.service.IFileService;
import com.pcdack.oscsmall.service.IProductService;
import com.pcdack.oscsmall.service.IUserService;
import com.pcdack.oscsmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by pcdack on 17-9-10.
 *
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;



    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }
    }

    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,
                                        Integer status)
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
           return iProductService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }
    }
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session,Integer productId){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }
    }
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize )
    {
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            return iProductService.getProductList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }
    }
    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,
                                        String productName,
                                        int productId,
                                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize)
    {
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(user);
        if (response.isSuccess()){
            return iProductService.searchProduct(productId,productName,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false)MultipartFile file,
                                 HttpServletRequest request)
    {

        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        ServerResponse response=iUserService.checkAdmin(currentUser);
        if (response.isSuccess()){
            String path=request.getSession().getServletContext().getRealPath("upload");
            String targetFileName=iFileService.upload(file,path);
            String url= PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap= Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("无限权操作");
        }
    }
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false)MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    {

        //富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        Map resultMap=Maps.newHashMap();
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser==null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录");
            return resultMap;
        }
        ServerResponse serverResponse=iUserService.checkAdmin(currentUser);
        if (serverResponse.isSuccess()){
            String path=request.getSession().getServletContext().getRealPath("upload");
            String targetFileName=iFileService.upload(file,path);
            if (org.apache.commons.lang3.StringUtils.isBlank(targetFileName)) {
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url= PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无限权操作");
            return resultMap;
        }
    }
}
