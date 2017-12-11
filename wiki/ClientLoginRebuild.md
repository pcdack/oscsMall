# 这可能是最简洁的登录界面－－（我的电商项目界面美化之旅）

> 先介绍一下我的[开源电商项目](https://github.com/pcdack/oscsMall),这个项目包含了安卓客户端的代码和Spring服务端代码，如果觉得不错，请`star`，如果觉得太丑请用`star`将我砸醒。我写这篇文章的目的是对这个项目的客户端进行美化的过程。有错误的地方，还请支出

## 为什么要美化界面

美化界面的原因一方面是提高自己对View的理解，另一方面也是最重要的方面，就是，很多同学像我吐槽说，我的项目的界面太丑。其实我觉的还`OK`了。所以，就这样开始我的美化之旅

## 美化前，美化后

**美化前**

![Login](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/Login.png)

![](https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=461445711,2447505874&fm=58&bpow=197&bpoh=194)

好像不是太丑的样子

**美化后**



![LoginGif](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/LoginGif.gif)

有没有感觉整个软件的逼格高起来了

# 过程



## 设计界面

![设计](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/设计.jpg)

一个程序猿应当以字符界面为美。。。

所以我只能搜索一些设计网站看看有没有别人的ＵＩ视图，可以供我参考使用，于是我发现了下面三个网站

* [dribbble](https://dribbble.com/)　里面的设计师画图又好看，长得又漂亮，我超喜欢哪里的。
* [behance](http://behance.net) 一个逗逼公司的产品，里面有很多高质量的UI设计图
* [UI中国](http://www.ui.cn/)emmmmm,也是不错的，毕竟中文更加亲切

于是，我在dribbble发现了[ta](https://dribbble.com/shots/2620750-Email-Client-Login-Screen),第一眼就爱上它了。



## 代码设计

### 设计难点

##### 波浪选择

能够生成波浪的，据我所知有三种方式

* [贝塞尔曲线生成，相关文章](http://blog.csdn.net/qq_30379689/article/details/53098481)
* [正弦函数模拟](https://www.cnblogs.com/itcqx/p/5557273.html)
* [图像的混合模式](https://www.cnblogs.com/itcqx/p/5557273.html)

图像混合模式，是用来生成非规则波的，所以忽略这个选项。于是在贝塞尔和正弦中选择。

![](http://cdnimg103.lizhi.fm/audio_cover/2016/07/15/29953678537524487_320x320.jpg)

**如何选择**

我想到的第一点是通过性能，来测试两种曲线生成方式的好坏

*测试贝塞尔波与正弦生成的优劣*

第一步写代码

![代码](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/代码.jpg)

因为上面的介绍文章里面有详细的代码，我这里就不复制了（需要略作修改，将速度调成一致，控制变量法）。

附上性能截图

> 贝塞尔

![贝塞尔](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/选区_027.png)

> 正弦函数模拟生成

![模拟生成](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/选区_028.png)



这里比较内存可以看出来，两者在内存占用上没有优劣之分，其中贝塞尔有一小点内存抖动，这里我们就忽略掉它。在来看ＣＰＵ，这里的差距就很明显了，正弦函数，无论从占用和稳定性上，都比贝塞尔好很多。

**结论**

所以，我选择正选函数模拟生成

## 知识点

- EditText的样式修改
- 自定义View
- emmmmm



![](https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=461445711,2447505874&fm=58&bpow=197&bpoh=194)



