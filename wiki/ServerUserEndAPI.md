# 用户后台ＡＰＩ



## 登录

**接口**

`/manage/user/login.do`

**参数**

username,password

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "登录成功",
"data":{
"id": 22,
"username": "pcdack",
"password": "",
"avatar": "",
"email": "",
"phone": "",
"question": "问题",
"answer": "答案",
"role": 1,
"createTime": 1509938261000,
"updateTime": 1509954751000
}
}
```



