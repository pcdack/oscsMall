package com.pcdack.oscsmall.service.Impl;

import com.google.common.collect.Lists;
import com.pcdack.oscsmall.common.Const;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.dao.CategoryMapper;
import com.pcdack.oscsmall.dao.ProductMapper;
import com.pcdack.oscsmall.pojo.Category;
import com.pcdack.oscsmall.pojo.Product;
import com.pcdack.oscsmall.service.IIndexService;
import com.pcdack.oscsmall.util.PropertiesUtil;
import com.pcdack.oscsmall.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServiceImpl implements IIndexService{
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;




    @Override
    public ServerResponse getIndex(int pageNum) {
        //第一步获取第一层CateGory信息
        List<Category> categories=categoryMapper.selectChildrenCategoryByParentId(Const.CategoryConst.DefaultCategory);
        if (CollectionUtils.isEmpty(categories)){
            return ServerResponse.createByErrorMessage("获取列表错误哦");
        }
        IndexVo indexVo=new IndexVo();
        if (pageNum == 1) {
            List<CategoryVo> categoryVo = assembleIndexVo(categories);
            indexVo.setCategoryVos(categoryVo);
        }
        //获取商品列表和Category信息
        //获取ChildCategory
        //获取ChildCategory的ProductId
        MyPageVo myPageVo=new MyPageVo();
        myPageVo.setPageNum(pageNum);

        List<CategoryWithProductVo> categoryWithProductVoList= Lists.newArrayList();
        if (pageNum==categories.size())
            myPageVo.setNextPage(false);
        if (pageNum==1)
            myPageVo.setPrePage(false);
        else
            myPageVo.setPrePage(true);
        Category category=categories.get(pageNum-1);
        List<Category> childs=categoryMapper.selectChildrenCategoryByParentId(category.getId());
        for (Category child : childs) {
            CategoryWithProductVo categoryWithProductVo=new CategoryWithProductVo();
            categoryWithProductVo.setId(child.getId());
            categoryWithProductVo.setStatus(child.getStatus());
            categoryWithProductVo.setName(child.getName());
            categoryWithProductVo.setCategoryImg(child.getImg());
            List<Product> products=productMapper.selectByCategoryId(child.getId());
            List<ProductListVo> productListVos= Lists.newArrayList();
            if (products.size()>Const.Index.MaxItemCount){
                for (int i = 0; i < Const.Index.MaxItemCount; i++) {
                    Product productItem=products.get(i);
                    productListVos.add(assembleProductList(productItem));
                }
            }else {
                for (Product productItem : products) {
                    productListVos.add(assembleProductList(productItem));
                }
            }
            categoryWithProductVo.setProductListVos(productListVos);
            categoryWithProductVoList.add(categoryWithProductVo);
        }
        indexVo.setCategoryWithProductVos(categoryWithProductVoList);
        indexVo.setMyPageVo(myPageVo);
        return ServerResponse.createBySuccess(indexVo);
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


    private List<CategoryVo> assembleIndexVo(List<Category> categories) {
        List<CategoryVo> categoryVos=new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo=new CategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setName(category.getName());
            categoryVo.setImage(category.getImg());
            List<Category> childs=categoryMapper.selectChildrenCategoryByParentId(category.getId());
            categoryVo.setChildVos(assembleChildVo(childs));
            categoryVos.add(categoryVo);
        }
        return categoryVos;
    }

    private List<CategoryChildVo> assembleChildVo(List<Category> childs) {
        List<CategoryChildVo> categoryChildVos=new ArrayList<>();
        for (Category child : childs) {
            CategoryChildVo childVo=new CategoryChildVo();
            childVo.setName(child.getName());
            childVo.setParentId(child.getParentId());
            childVo.setId(child.getId());
            child.setStatus(child.getStatus());
            categoryChildVos.add(childVo);
        }
        return categoryChildVos;
    }
}