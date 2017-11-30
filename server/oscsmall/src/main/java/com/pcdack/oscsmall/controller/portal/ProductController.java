package com.pcdack.oscsmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by pcdack on 17-9-11.
 *
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getProductDetail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse<PageInfo> getProductList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return iProductService.getProductList(pageNum,pageSize);
    }
    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> searchProductList(@RequestParam(value = "keyword",required = false) String keyword,
                                                      @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeyWordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
    @RequestMapping(value = "searchByKeyWord.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> searchProductList(@RequestParam(value = "keyword",required = false) String keyword,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeyWord(keyword, pageNum, pageSize, orderBy);
    }
}
