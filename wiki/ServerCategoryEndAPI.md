# 商品分类前台

## 添加类型

**接口**

`/manage/category/add_category.do`

**参数**

categoryName，parentId（父ＩＤ）,imageUrl

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg":"添加成功"
}
```



## 获取所有子分类

**接口**

`/manage/category/get_deep_category.do`

**参数**

category，０是根节点

**请求方式**

POST

**请求结果**

```json
{
  “status”:0,
  "msg":100030,100040.....
}
```



## 获取类型

**接口**

`/manage/category/get_category.do`

**参数**

categoryId

**请求方式**

POST

**请求结果**

## 设置图片

**接口**

`/manage/category/set_category_image.do`

**参数**

categoryId

imageUrl

**请求方式**

POST

**请求结果**

```json
{
  "status":0,
  "msg":"设置成功"
}
```



## 设置名字

**接口**

`/manage/category/set_category_name.do`

**参数**

categoryId

categoryName

**请求方式**

POST

**请求结果**

```json
{
  "status":0,
  "msg":"设置成功"
}
```

