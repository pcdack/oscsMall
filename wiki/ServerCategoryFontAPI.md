# 商品分类前台

## 获取分类的商品

**接口**

`/category/get_category.do`

**参数**

category，０是根节点

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":[
{"id": 100032, "parentId": 0, "name": "家用电器", "img": null, "status": true,…},
{"id": 100033, "parentId": 0, "name": "数码3C", "img": null, "status": true,…},
{"id": 100034, "parentId": 0, "name": "食品饮料", "img": null, "status": true,…},
{"id": 100035, "parentId": 0, "name": "极客数码", "img": null, "status": true,…},
{"id": 100036, "parentId": 0, "name": "发烧玩家", "img": null, "status": true,…},
{"id": 100037, "parentId": 0, "name": "清洁用品", "img": null, "status": true,…}
]
}
```



## 获取所有子分类

**接口**

`/category/get_deep_category.do`

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

