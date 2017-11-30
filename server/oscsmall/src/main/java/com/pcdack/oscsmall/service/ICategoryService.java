package com.pcdack.oscsmall.service;


import com.pcdack.oscsmall.common.ServerResponse;
import com.pcdack.oscsmall.pojo.Category;

import java.util.List;

/**
 * Created by pcdack on 17-9-9.
 *
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse addCategory(String categoryName, Integer parentId,String imageUrl);
    ServerResponse updateCategoryName(Integer categoryId, String categoryName);
    ServerResponse<List<Category>> getChildrenCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

    ServerResponse updateCategoryImage(Integer categoryId, String imageUrl);
}
