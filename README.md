# 一个Ａｌｍｏｓｔ完整的电商项目后台＋安卓客户端

一转眼就已经大三了，作为非ＣＳ专业的一个普通大学生，应该对自己的未来做规划了。于是，大三开始我就开始着手准备一个“大项目”。将前两年学习的知识做一个总结，归纳。于是就有了这个项目。项目的名字叫OscsMall,为什么叫这个名字我也不知道。至于Ａｌｍｏｓｔ是因为整个项目缺少了支付部分的代码，原因是拿不到微信的ｋｅｙ。整个项目分为后端和客户端，后端代码显得稚嫩，客户端代码也算拿的的出手。还恳请个位大佬轻喷。项目自然参考了其他大神的代码。这个项目我也会一直维护下去的，恳请大佬们ｓｔａｒ一下。



## 后台

整个项目使用Ｓｐｒｉｎｇ＋ＭｙＢａｔｉｓ＋ＭｙＳＱＬ，项目采用ＭＶＣ模式。包含前台和后台代码。是一个很普通的电商项目，包含了８大结构，６大模块。

### 项目结构

#### ｃｏｍｍｏｎ

作用：用来处理公共的信息，包括Ｔｏｋｅｎ，常量，全局异常，公共类等封装

#### ｃｏｎｔｒｏｌｌｅｒ

作用：包括前台和后台部分的`controller`代码

#### service

作用：项目中要到的`service`部分

### dao

作用：负责ｄａｏ层

#### pojo

作用：数据库对象

#### vo

作用：ｖｉｅｗ　ｏｂｊｅｃｔ

#### util

作用：一些简单的工具类

### 项目模块(详情见文档)

#### 用户信息

用户登录等管理

#### 商品信息

商品信息管理

#### 商品分类信息

商品分类管理

#### 购物车

购物车添加等管理

#### 订单

订单生成等管理

#### 地址

地址增加等功能



##　客户端

#### 客户端的整体架构

![App架构](/media/pcdack/SoftWaveDevTools/OscMall/client/客户端骨架搭建/App架构.png)

整体采用了单Ａｃｔｉｖｉｔｙ多ｆｒａｇｍｅｎｔ的设计思路

#####  网络模块（两种）

1.  使用rxJava与retrofit2和okhttp3组成网络请求框架


2. 未使用rxJava的网络框架，整个请求类似于Velloy

##### Ａｐｐ配置相关

* 整个Ａｐｐ的图标全部使用ｉｃｏｎ
* 使用Ｂｕｉｌｄｅｒ设计模式来配置整个应用信息(使用什么ｉｃｏｎ)

##### 代理Ａｃｔｉｖｉｔｙ

作为整个程序唯一一个Ａｃｔｉｖｉｔｙ，作为所有Ｆｒａｇｍｅｎｔ的代理

##### ＭＶＰ相关

作为ＭＶＰ模式的基类

##### UI相关

ＵＩ处理部分





#### Ａｐｐ的整体流程



![客户端整体架构(Release final)](/media/pcdack/SoftWaveDevTools/OscMall/client/客户端骨架搭建/客户端整体架构(Release final).png)



##### App的整体流程分为三部分

1. 首页展示前，整个Ａｃｔｉｖｉｔｙ作为Ｆｒａｇｍｅｎｔ的调度站，负责Ｆｒａｇｍｅｎｔ的跳转和信息传递。这样的好处是，避免跳转流程复杂，导致自己开发时候很晕。一般Ｆｒａｇｍｅｎｔ的跳转需要Ａｐｐ的当前状态，用户的当前状态，通过这两状态去决定下一个Ｆｒａｇｍｅｎｔ是什么。
2. 首页展示后，整个Ｂｏｔｔｏｍ　Ｆｒａｇｍｅｎｔ作为调度站，负责在各个页面之间进行跳转。
3. 不同首页需要其他额外的Ｆｒａｇｍｅｎｔ，由该Ｆｒａｇｍｅｎｔ自己掌控。

##### 目的：

Ａｐｐ整体流程是我自己的一些小技巧的总结，让同级的Ｆｒａｇｍｅｎｔ之间的调用更加清楚，这样设计,如果Ａｐｐ加入新的类型的启动页，我们就不用更改相关所有Ｆｒａｇｍｅｎｔ的代码，而只需要更改Ａｃｔｉｖｉｔｙ的代码就可以了。



### 使用到的依赖

```
//iconify icon图标库
compile 'com.joanzapata.iconify:android-iconify:2.2.2'
compile 'com.joanzapata.iconify:android-iconify-fontawesome:2.2.2'
// (v4.5)
compile 'com.joanzapata.iconify:android-iconify-entypo:2.2.2'
// (v3,2015)
compile 'com.joanzapata.iconify:android-iconify-typicons:2.2.2'
// (v2.0.7)
compile 'com.joanzapata.iconify:android-iconify-material:2.2.2'
// (v2.0.0)
compile 'com.joanzapata.iconify:android-iconify-material-community:2.2.2'
// (v1.4.57)
compile 'com.joanzapata.iconify:android-iconify-meteocons:2.2.2'
// (latest)
compile 'com.joanzapata.iconify:android-iconify-weathericons:2.2.2'
// (v2.0)
compile 'com.joanzapata.iconify:android-iconify-simplelineicons:2.2.2'
// (v1.0.0)
compile 'com.joanzapata.iconify:android-iconify-ionicons:2.2.2'
// (v2.0.1)
//AndroidUtilCode安卓工具类
compile 'com.blankj:utilcode:1.9.8'
//logger
compile 'com.orhanobut:logger:2.1.1'
//网络请求依赖
compile 'com.squareup.okio:okio:1.13.0'
compile 'com.squareup.okhttp3:okhttp:3.8.1'
compile 'com.squareup.retrofit2:retrofit:2.3.0'
compile 'com.squareup.retrofit2:converter-scalars:2.3.0'
//rx全家桶
compile 'io.reactivex.rxjava2:rxjava:2.1.6'
compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
//JSON依赖Android版
compile 'com.alibaba:fastjson:1.1.57.android'
//AVLoadIndicatorView　加载
compile 'com.wang.avi:library:2.1.3'
// fragmentation　Ｆｒａｇｍｅｎｔ
compile 'me.yokeyword:fragmentation:1.1.8'
compile 'me.yokeyword:fragmentation-swipeback:1.1.8'
//ButterKnife依赖
compile 'com.jakewharton:butterknife:8.6.0'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.6.0'
//Banner依赖
compile 'com.bigkoo:convenientbanner:2.0.5'
compile 'com.ToxicBakery.viewpager.transforms:view-pager-transforms:1.2.32@aar'
//RecyclerView依赖
compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.32'
//RecyclerView分割线
compile 'com.choices.divider:RecyclerView_Divider:1.0.0'
//StatusBarCompat
dependencies {
    compile('com.github.niorgai:StatusBarCompat:2.1.4', {
        exclude group: 'com.android.support'
    })
}
//Google AutoValue provided代表只在编译期间，产生作用，最后在源码中是不存在的
provided 'com.google.auto.value:auto-value:1.4.1'
annotationProcessor "com.google.auto.value:auto-value:1.4.1"
//图片加载
compile 'com.github.bumptech.glide:glide:4.0.0-RC0'
compile 'com.github.bumptech.glide:okhttp3-integration:4.0.0-RC0@aar'
annotationProcessor 'com.github.bumptech.glide:compiler:4.0.0-RC0'
//circleimageview　圆形头像
compile 'de.hdodenhof:circleimageview:2.2.0'
//数据库依赖
compile 'org.greenrobot:greendao-generator:3.2.2'
compile 'org.greenrobot:greendao:3.2.2'
//图片裁剪
compile 'com.github.yalantis:ucrop:2.2.1-native'
//permissionsdispatcher
compile("com.github.hotchemi:permissionsdispatcher:3.0.1") {
    exclude module: "support-v13"
}
annotationProcessor "com.github.hotchemi:permissionsdispatcher-processor:3.0.1"
//富文本
compile 'com.zzhoujay.richtext:richtext:2.5.4'
//二维码扫描
compile 'me.dm7.barcodescanner:zbar:1.9.3'
```

##### 



