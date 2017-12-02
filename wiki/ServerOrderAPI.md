# 订单部分

>所有的pageNum和pageSize默认值为１和１０，适用整个项目



## 创建

**接口**

`/order/create.do`

**参数**

shippingId

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"orderNo": 1511766419593,
"payment": 32967,
"paymentType": 1,
"paymentTypeDesc": "在线支付",
"postage": 0,
"status": 10,
"statusDesc": "未支付",
"paymentTime": "",
"sendTime": "",
"endTime": "",
"closeTime": "",
"createTime": "",
"orderItemVoList":[{"orderNo": 1511766419593, "productId": 31, "productName": "米家行车记录仪", "productImage": "https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/ab0a74d8bee6ddca9369bf202a1b2b3a.jpg?bg=FFFFFF",…],
"imageHost": "http://img.mmall.com/",
"shippingId": 34,
"receiverName": "liYu",
"shippingVo":{"receiverName": "liYu", "receiverPhone": "7760773123", "receiverMobile": "13538607151", "receiverProvince": "广东",…}
}
}
```



## 详情

**接口**

`/order/detail.do`

**参数**

orderNo

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
{"orderNo": 1510059421774, "payment": 10794, "paymentType": 1, "paymentTypeDesc": "在线支付",…}
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

## 取消

**接口**

`/order/cancel.do`

**参数**

orderNo

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"orderItemVoList":[
{
"orderNo": null,
"productId": 30,
"productName": "创维（Skyworth）高清智能电视(金色)",
"productImage": "https://img13.360buyimg.com/n1/jfs/t5515/101/1122644946/76048/20cb62a8/590be863N20dd5dfc.jpg",
"currentUnitPrice": 3598,
"quantity": 3,
"totalPrice": 10794,
"createTime": ""
}
],
"productTotalPrice": 10794,
"imageHost": "http://img.mmall.com/"
}
}
```



## 列表

**接口**

`/order/list.do`

**参数**

**请求方式**

GET or POST

**请求结果**

```json
{
"status": 0,
"data":{
"pageNum": 1,
"pageSize": 10,
"size": 4,
"orderBy": null,
"startRow": 1,
"endRow": 4,
"total": 4,
"pages": 1,
"list":[{"orderNo": 1510059421774, "payment": 10794, "paymentType": 1, "paymentTypeDesc": "在线支付",…],
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



## 获取未支付

**接口**

`/order/no_pay_list.do`

**参数**



**请求方式**

GET or POST

**请求结果**

```json
{
"status": 0,
"data":{
"pageNum": 1,
"pageSize": 10,
"size": 4,
"orderBy": null,
"startRow": 1,
"endRow": 4,
"total": 4,
"pages": 1,
"list":[
{"orderNo": 1510059421777, "payment": 10794, "paymentType": 1, "paymentTypeDesc": "在线支付",…}
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



## 订单商品信息

**接口**

`/order/order_msg_product.do`

**参数**

shippingId

**请求方式**

GET or POST

**请求结果**

```json
{
"status": 0,
"data":{
"orderNo": 1510059421774,
"payment": 10794,
"paymentType": 1,
"paymentTypeDesc": "在线支付",
"postage": 0,
"status": 0,
"statusDesc": "已取消",
"paymentTime": "",
"sendTime": "",
"endTime": "",
"closeTime": "",
"createTime": "2017-11-07 20:57:01",
"orderItemVoList":[
{"orderNo": 1510059421774, "productId": 30, "productName": "创维（Skyworth）高清智能电视(金色)", "productImage": "https://img13.360buyimg.com/n1/jfs/t5515/101/1122644946/76048/20cb62a8/590be863N20dd5dfc.jpg",…}
],
"imageHost": "http://img.mmall.com/",
"shippingId": 34,
"receiverName": "李雨杭杭",
"shippingVo":{"receiverName": "凉凉", "receiverPhone": "7760773123", "receiverMobile": "13538607151", "receiverProvince": "广东",…}
}
}
```

