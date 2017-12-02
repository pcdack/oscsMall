# 用户地址ＡＰＩ

## 添加

**接口**

`/shipping/add.do`

**参数**

receiverName,receiverPhone,receiverMobile,receiverProvince,receiverCity,receiverDistrict,receiverAddress,receiverZip

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "新建地址成功"
}
```

## 删除

**接口**

`/shipping/del.do`

**参数**

shippingId

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "更新地址成功",

}
```

## 列表

**接口**

`/shipping/list.do`

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
"size": 2,
"orderBy": null,
"startRow": 1,
"endRow": 2,
"total": 2,
"pages": 1,
"list":[
{"id": 34, "userId": 22, "receiverName": "liYu", "receiverPhone": "7760773123",…},
{"id": 35, "userId": 22, "receiverName": "李雨杭", "receiverPhone": "776077",…}
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

## 选择

**接口**

`/shipping/select.do`

**参数**

shippingId

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "选择地址成功",
"data":{
"id": 34,
"userId": 22,
"receiverName": "liYu",
"receiverPhone": "7760773123",
"receiverMobile": "13538607151",
"receiverProvince": "广东",
"receiverCity": "东莞",
"receiverDistrict": "松山湖区",
"receiverAddress": "大学路一号",
"receiverZip": "51000",
"createTime": null,
"updateTime": 1511437357000
}
}
```



## 更新

**接口**

`/shipping/update.do`

**参数**

receiverName,receiverPhone,receiverMobile,receiverProvince,receiverCity,receiverDistrict,receiverAddress,receiverZip

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
{"id": 34, "userId": 22, "receiverName": "李雨杭杭", "receiverPhone": "7760773123",…}
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

