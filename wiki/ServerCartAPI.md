# 购物车ＡＰＩ

## 添加

**接口**

`/cart/add.do`

**参数**

count 数量

productId　商品ＩＤ

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"cartProductVoList":[
{"id": 157, "userId": 22, "productId": 41, "quantity": 1, "productName": "小米Max2",…}
],
"allPrices": 1599,
"allChecked": true,
"imageHost": "http://img.mmall.com/"
}
}
```



## 删除

**接口**

`/cart/delete_product.do`

**参数**

productIds,如果有多个ＩＤ，就用`,`　隔开。

**请求方式**

POST

**请求结果**

```json
{
  "status":0,
  "msg":"删除成功"
  
}
```



## 列表

**接口**

`/cart/list.do`

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
"cartProductVoList":[{"id": 151, "userId": 22, "productId": 31, "quantity": 3,…],
"allPrices": 32967,
"allChecked": true,
"imageHost": "http://img.mmall.com/"
}
}
```



## 选择

**接口**

`/cart/select.do`

**参数**

productId

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data": 3
}
```



## 全选

**接口**

`/cart/select_all.do`

**参数**

**请求方式**

POST or GET

**请求结果**

```json
{
"status": 0,
"data":{
"cartProductVoList":[
{"id": 148, "userId": 22, "productId": 30, "quantity": 3, "productName": "小米Note3",…}
],
"allPrices": 6897,
"allChecked": true,
"imageHost": "http://img.mmall.com/"
}
}	
```



## 取消选择

**接口**

`/cart/un_select.do`

**参数**

productId

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"data":{
"cartProductVoList":[
{"id": 148, "userId": 22, "productId": 30, "quantity": 3, "productName": "小米Note3",…}
],
"allPrices": 0,
"allChecked": false,
"imageHost": "http://img.mmall.com/"
}
}
```



## 取消全选

**接口**

`/cart/un_select_all.do`

**参数**

**请求方式**

POST or GET

**请求结果**

```json
{
"status": 0,
"data":{
"cartProductVoList":[
{"id": 148, "userId": 22, "productId": 30, "quantity": 3, "productName": "小米Note3",…}
],
"allPrices": 0,
"allChecked": false,
"imageHost": "http://img.mmall.com/"
}
}
```



## 更新

**接口**

`/cart/update.do`

**参数**

count,productId

**请求方式**

POST or GET

**请求结果**

```json
{
"status": 0,
"data":{
"cartProductVoList":[
{"id": 148, "userId": 22, "productId": 30, "quantity": 2, "productName": "小米Note3",…}
],
"allPrices": 0,
"allChecked": false,
"imageHost": "http://img.mmall.com/"
}
}
```

