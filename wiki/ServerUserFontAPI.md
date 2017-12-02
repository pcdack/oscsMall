# 用户前台

## 登录

**接口**

`/user/login.do`

**参数**

username,password

**请求方式**

限定为ＰＯＳＴ

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
"role": 0,
"createTime": 1509938261000,
"updateTime": 1509954751000
}
}
```



## 注册

**接口**

`/user/register.do`

**参数**

username,password,email,phone,question,answer

**请求方式**

ＰＯＳＴ

**请求结果**

```json
{
"status": 0,
"msg": "注册成功"
}
```



## 登出

**接口**

`/user/logout.do`

**参数**

**请求方式**

GET 或ＰＯＳＴ

**请求结果**

```json
{
  "status":0,
  "msg":"登出成功"
}
```





## 当前用户信息获取

**接口**

`/user/get_information.do`

**参数**

**请求方式**

GET 或ＰＯＳＴ

**请求结果**

```json
{
"status": 0,
"data":{
"id": 22,
"username": "pcdack",
"password": "",
"avatar": "",
"email": "",
"phone": "",
"question": "question",
"answer": "answer",
"role": 0,
"createTime": 1509938261000,
"updateTime": 1509954751000
}
}
```



## 验证信息

**接口**

`/user/check_valid.do`

**参数**

type 参数类型ｅｍａｉｌ是邮箱类型,username是用户名类型

str 需要验证的

**请求方式**

POST

**请求结果**

```json
{
"status": 1,
"msg": "用户已存在"
}
```



## 用户信息更新

**接口**

`/user/update_information.do`

**参数**

用户的任意属性

username

"password"

"avatar"

"email"

"phone"

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "个人信息更新成功",
"data":{
"id": 22,
"username": "pcdack",
"password": null,
"avatar": "xxxx",
"email": "",
"phone": "",
"question": null,
"answer": null,
"role": null,
"createTime": null,
"updateTime": null
}
}
```



## 密码重置问题

**接口**

`/user/forget_get_question.do`

**参数**

username

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "问题"
}
```



## 密码重置验证



**接口**

`/user/forget_check_answer.do`

**参数**

username,question,answer

**请求方式**

POST

**请求结果**

```json
{
"status": 0,
"msg": "31df3b63-592e-4488-bfc5-66aa904e5db9"
}
```



## 密码重置

**接口**

`/user/forget_reset_password.do`

**参数**

username,newPassword,forgetToken(由密码重置验证可以得到)

**请求方式**

POST

**请求结果**

```json
{
  "status":0,
  "msg":"修改成功"
}
```







