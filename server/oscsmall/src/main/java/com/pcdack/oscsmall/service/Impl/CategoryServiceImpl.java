package com.pcdack.oscsmall.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.dao.CategoryMapper;
import com.pcdack.oscsmall.pojo.Category;
import com.pcdack.oscsmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private CategoryMapper categoryMapper;
    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);


    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        return addCategoryWithImage(categoryName,parentId,null);
    }

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId, String imageUrl) {
        return addCategoryWithImage(categoryName,parentId,imageUrl);

    }

    private ServerResponse addCategoryWithImage(String categoryName, Integer parentId, String imageUrl) {
        if (parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("增加失败，参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        if (StringUtils.isNoneEmpty(imageUrl))
            category.setImg(imageUrl);
        category.setStatus(true);
        int resultCode=categoryMapper.insert(category);
        if (resultCode > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }


    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId ==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("增加失败，参数错误");
        }
        Category category=new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCode=categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCode>0){
            return ServerResponse.createBySuccess("更新品类信息成功");
        }
        return ServerResponse.createByErrorMessage("更新品类信息错误");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenCategory(Integer categoryId) {
        List<Category> categoryList=categoryMapper.selectChildrenCategoryByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet= Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> idList= Lists.newArrayList();
        if (categoryId!=null){
            for (Category category : categorySet) {
                idList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(idList);
    }

    @Override
    public ServerResponse updateCategoryImage(Integer categoryId, String imageUrl) {
        if (categoryId ==null || StringUtils.isBlank(imageUrl)){
            return ServerResponse.createByErrorMessage("增加失败，参数错误");
        }
        Category category=new Category();
        category.setId(categoryId);
        category.setImg(imageUrl);
        int rowCode=categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCode>0){
            return ServerResponse.createBySuccess("更新品类信息成功");
        }
        return ServerResponse.createByErrorMessage("更新品类信息错误");
    }

    //递归层级调用
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category=categoryMapper.selectByPrimaryKey(categoryId);
        if (category!=null){
            categorySet.add(category);
        }
        List<Category> categories=categoryMapper.selectChildrenCategoryByParentId(categoryId);
        for (Category category1 : categories) {
            findChildCategory(categorySet,category1.getId());
        }

        return categorySet;
    }
}