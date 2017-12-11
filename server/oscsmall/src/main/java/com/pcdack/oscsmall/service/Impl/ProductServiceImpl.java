package com.pcdack.oscsmall.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ResponseCode;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.dao.CategoryMapper;
import com.pcdack.oscsmall.dao.ProductMapper;
import com.pcdack.oscsmall.pojo.Category;
import com.pcdack.oscsmall.pojo.Product;
import com.pcdack.oscsmall.service.ICategoryService;
import com.pcdack.oscsmall.service.IProductService;
import com.pcdack.oscsmall.util.PropertiesUtil;
import com.pcdack.oscsmall.util.TimeUtil;
import com.pcdack.oscsmall.vo.ProductDetailVo;
import com.pcdack.oscsmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iProductService")
public class ProductServiceImpl implements IProductService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product!=null){
            if (StringUtils.isNotBlank(product.getSubImages()) && product.getSubImages().split(",").length>0){
                product.setMainImage(product.getSubImages().split(",")[0]);
            }
            if (product.getId()!=null){
                int rowCount=productMapper.updateByPrimaryKey(product);
                if (rowCount>0){
                    return ServerResponse.createBySuccess("更新数据成功");
                }else {
                    return ServerResponse.createByErrorMessage("更新数据失败");
                }
            }else {
                int rowCount=productMapper.insert(product);
                if (rowCount>0){
                    return ServerResponse.createBySuccess("新增成功");
                }else {
                    return ServerResponse.createByErrorMessage("新增失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("新增数据或者更新数据失败");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId==null || status ==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCode=productMapper.updateByPrimaryKeySelective(product);
        if (rowCode>0)
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        return ServerResponse.createByErrorMessage("修改产品销售状态实在");
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId ==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createByErrorMessage("商品下架或者不存在");
        }
        ProductDetailVo productDetailVo=assembleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }
    @Override
    public ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize) {
        if (pageNum ==null || pageSize ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products= productMapper.selectList();
        List<ProductListVo> productListVos= Lists.newArrayList();
        for (Product productItem : products) {
            productListVos.add(assembleProductList(productItem));
        }
        @SuppressWarnings("unchecked")
        PageInfo pageResult=new PageInfo(products);
        //noinspection unchecked
        pageResult.setList(productListVos);
        return ServerResponse.createBySuccess(pageResult);
    }



    @Override
    public ServerResponse<PageInfo> searchProduct(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName=new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> products=productMapper.selectByNameAndId(productName,productId);
        List<ProductListVo> productListVos=Lists.newArrayList();
        for (Product product : products) {
            productListVos.add(assembleProductList(product));
        }
        @SuppressWarnings("unchecked")
        PageInfo pageResult=new PageInfo(products);
        //noinspection unchecked
        pageResult.setList(productListVos);

        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId ==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),"参数错误");
        }
        Product product=productMapper.selectByPrimaryKey(productId);
        if (product==null){
            return ServerResponse.createByErrorMessage("商品下架或者不存在");
        }
        if (product.getStatus()!= Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("商品下架或者不存在");
        }
        ProductDetailVo productDetailVo=assembleProductDetailVo(product);

        return ServerResponse.createBySuccess(productDetailVo);
    }
    public ServerResponse<PageInfo> getProductByKeyWord(String keyword,
                                                        Integer pageNum,
                                                        Integer pageSize,
                                                        String orderBy){
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isBlank(keyword)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        keyword= "%" + keyword + "%";
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> products=productMapper.selectByKeyWord(keyword);
        List<ProductListVo> productListVos=Lists.newArrayList();
        for (Product product : products) {
            productListVos.add(assembleProductList(product));
        }
        @SuppressWarnings("unchecked")
        PageInfo pageResult=new PageInfo(products);
        //noinspection unchecked
        pageResult.setList(productListVos);
        return ServerResponse.createBySuccess(pageResult);
    }
    @Override
    public ServerResponse<PageInfo> getProductByKeyWordCategory(String keyword,
                                                                Integer categoryId,
                                                                Integer pageNum,
                                                                Integer pageSize,
                                                                String orderBy) {
        PageHelper.startPage(pageNum,pageSize);
        if (StringUtils.isBlank(keyword) && categoryId ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        List<Integer> categoryIds=Lists.newArrayList();
        if (categoryId !=null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> listVos=Lists.newArrayList();
                @SuppressWarnings("unchecked")
                PageInfo result=new PageInfo(listVos);
                return ServerResponse.createBySuccess(result);
            }
            categoryIds=iCategoryService.selectCategoryAndChildrenById(categoryId).getData();

        }
        if (StringUtils.isNotBlank(keyword)){
            keyword= "%" + keyword + "%";
        }

//        排序处理
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> products=productMapper.selectByNameAndCategoryId(keyword,categoryIds);
//        List<Product> products=productMapper.selectByCategoryId(categoryId);
        List<ProductListVo> productListVos=Lists.newArrayList();
        for (Product product : products) {
            productListVos.add(assembleProductList(product));
        }
        @SuppressWarnings("unchecked")
        PageInfo pageResult=new PageInfo(products);
        //noinspection unchecked
        pageResult.setList(productListVos);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> getProductByCategory(Integer categoryId, Integer pageNum, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNum,pageSize);
        if (categoryId ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> products=productMapper.selectByCategoryId(categoryId);
        List<ProductListVo> productListVos=Lists.newArrayList();
        for (Product product : products) {
            productListVos.add(assembleProductList(product));
        }
        @SuppressWarnings("unchecked")
        PageInfo pageResult=new PageInfo(products);
        //noinspection unchecked
        pageResult.setList(productListVos);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo=new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setName(product.getName());
        productDetailVo.setCategory_id(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setMain_image(product.getMainImage());
        productDetailVo.setSub_images(product.getSubImages());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.mmall.com/"));
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category==null){
            productDetailVo.setParentCategoryId(0);
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        productDetailVo.setCreateTime(TimeUtil.dateTostr(product.getCreateTime()));
        productDetailVo.setUpdateTime(TimeUtil.dateTostr(product.getUpdateTime()));
        return productDetailVo;
    }
    private ProductListVo assembleProductList(Product productItem) {
        ProductListVo productListVo=new ProductListVo();
        productListVo.setId(productItem.getId());
        productListVo.setName(productItem.getName());
        productListVo.setSubtitle(productItem.getSubtitle());
        productListVo.setIndex_kind(productItem.getIndexKind());
        productListVo.setSpan_size(productItem.getSpanSize());
        productListVo.setStatus(productItem.getStatus());
        productListVo.setPrice(productItem.getPrice());
        productListVo.setMain_image(productItem.getMainImage());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.mmall.com/"));

        return productListVo;
    }
}