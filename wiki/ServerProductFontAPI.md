# 商品前台

## 列表

**接口**

`/product/list.do`

**参数**

pageNum 默认为１

pageSize　默认为１０

**请求方式**

POST or GET

**请求结果**

```json
{
"status": 0,
"data":{
"pageNum": 1,
"pageSize": 10,
"size": 10,
"orderBy": null,
"startRow": 1,
"endRow": 10,
"total": 16,
"pages": 2,
"list":[{"id": 27, "name": "小米5X", "subtitle": "摄影，拍人更美", "main_image": "https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/f193b272c16c01ddc4831f08313bed24.jpg?bg=FFFFFF",…],
"firstPage": 1,
"prePage": 0,
"nextPage": 2,
"lastPage": 2,
"isFirstPage": true,
"isLastPage": false,
"hasPreviousPage": false,
"hasNextPage": true,
"navigatePages": 8,
"navigatepageNums":[
1,
2
]
}
}
```



## 详情

**接口**

`/product/detail.do`

**参数**

productId

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"id": 30,
"category_id": 100033,
"name": "小米Note3",
"subtitle": null,
"main_image": "https://i8.mifile.cn/v1/a1/2e13efd1-173d-7fd3-66e5-47933c192249.webp?bg=3E5592",
"sub_images": "https://i8.mifile.cn/v1/a1/8ee236a2-ed5f-a1a9-a16a-d5ddede18df7.webp,https://i8.mifile.cn/v1/a1/0708ed8d-f27e-833f-63ac-4704ef1872dc.webp,https://i8.mifile.cn/v1/a1/5d851d99-e7b2-21dd-73fd-f5bba6340711.webpg",
"detail": "<p><img alt=\"shang.jpg\" src=\"https://i8.mifile.cn/v1/a1/6b20c16b-7272-e5c1-c72e-e8ef70eb6a84!720x7200.jpg\" width=\"790\" height=\"444\"/> &nbsp;<br/></p>",
"price": 2299,
"stock": 1500,
"status": 1,
"createTime": "2017-11-07 09:50:43",
"updateTime": "2017-11-07 09:50:43",
"imageHost": "http://img.mmall.com/",
"parentCategoryId": 0
}
}
```



##  通过类型搜索

**接口**

`/product/search.do`

**参数**

keyword关键字

category分类

pageNum 页数默认１

pageSize 默认１０

orderBy排序

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"pageNum": 1,
"pageSize": 10,
"size": 1,
"orderBy": null,
"startRow": 1,
"endRow": 1,
"total": 1,
"pages": 1,
"list":[
{"id": 45, "name": "创维（Skyworth）高清智能电视(金色)", "subtitle": "【11.11提前抢】超薄全金属4K，薄至11mm，人工智能，HDR！评论晒图再送价值498元电视爱奇艺会员“另有创维58吋全面屏电视3999抢“(此商品不参加上述活动)",…}
],
"firstPage": 1,
"prePage": 0,
"nextPage": 0,
"lastPage": 1,
"isFirstPage": true,
"isLastPage": true,
"hasPreviousPage": false,
"hasNextPage": false,
"navigatePages": 8,
"navigatepageNums":[
1
]
}
}
```

## 通过关键字搜索

**接口**

`/product/searchByKeyWord.do`

**参数**

keyword关键字

pageNum 页数默认１

pageSize 默认１０

orderBy排序

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"pageNum": 1,
"pageSize": 10,
"size": 1,
"orderBy": null,
"startRow": 1,
"endRow": 1,
"total": 1,
"pages": 1,
"list":[
{"id": 45, "name": "创维（Skyworth）高清智能电视(金色)", "subtitle": "【11.11提前抢】超薄全金属4K，薄至11mm，人工智能，HDR！评论晒图再送价值498元电视爱奇艺会员“另有创维58吋全面屏电视3999抢“(此商品不参加上述活动)",…}
],
"firstPage": 1,
"prePage": 0,
"nextPage": 0,
"lastPage": 1,
"isFirstPage": true,
"isLastPage": true,
"hasPreviousPage": false,
"hasNextPage": false,
"navigatePages": 8,
"navigatepageNums":[
1
]
}
}
```

