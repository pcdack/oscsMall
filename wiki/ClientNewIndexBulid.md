# 你们在准备双十二?我给我的电商开源项目准备了一个新首页（我的电商项目界面美化之旅）

> 先介绍一下我的[开源电商项目](https://github.com/pcdack/oscsMall)，[简书介绍](http://www.jianshu.com/p/5d8f6a018c3d),这个项目包含了安卓客户端的代码和Spring服务端代码，如果觉得不错，请`star`，如果觉得太丑请用`star`将我砸醒。我写这篇文章的目的是对这个项目的客户端进行美化的过程。有错误的地方，还请指出



先上图

![newIndex](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/newIndex.png)

首先这个界面的设计稿来自于[dribbble](https://dribbble.com/shots/3811180-E-commerce-App-Home-Screen-Design) ,其中有很多不一样的地方，主要原因还是我懒，其次是找不到素材。我的素材都是来自与网络自然有些限制。

## 技术难点

## 客户端

* 嵌套RecyclerView的处理，当然，这个是用库可以解决处理掉的
* 一开始我是用`FloatingActionButton`，来作为`Shop Now`的按钮，最后发现`FloatingActionButton`　的形状是不可能变化的，[资料](http://blog.csdn.net/tuke_tuke/article/details/78333667)　有关于`FloatingActionButton`的一些源码分析。
* `iconify`库导入新的`Icon`

## 服务器

* `Index`页面设计更加复杂，返回的数据里面包含了两种信息。当然我用最笨的方法，直接读取数据然后拼接，实现了效果，但是应该来讲效率不高。



## Iconify　自定义新的字体

#### 第一步下载图标

首先在[iconfont](http://www.iconfont.cn/)　中找到你中意的图标，然后下载下来

#### 第二步将ttf导入到项目的assets目录下

我们在iconfont下载的图标都是以rar压缩包的形式存在的。我们将icon解压，会得到

![选区_037](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/选区_037.png)

然后将iconfont.ttf导入到android studio。

#### 第三步，编写iconify的`FontDescriptor`

新建两个类

１．一个是枚举类

```java
public enum CategoryIcons implements Icon {
    icon_beatiful('\ue6de'),
    icon_fastion('\ue608'),
    icon_electronics('\ue68e');

    char character;
    CategoryIcons(char character) {
        this.character=character;
    }
    @Override
    public String key() {
        return this.name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
```



其中枚举中的('\u6de'),在我们刚刚下载图标的文件夹里面的demo_unicode.html里.

例如

![选区_038](/media/pcdack/SoftWaveDevTools/MyGitProject/OscsMallEc/pic/选区_038.png)

图标下面的一堆"乱码“就我们需要的东西，只要将前面的`&#x`替换成`\u`,去掉`;`就可以了。

２．然后实现`FontDescriptor`

```java
public class CategoryModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return CategoryIcons.values();
    }
}
```

> 其中iconfont.ttf是前面导入`ttf`的文件名

# 注意：这个界面并没有直接接到当前项目中，只是一个测试版（因为涉及到很多前台后端的东西,有很多bug,需要慢慢修复，最近恰逢期末考，等寒假再不断完善），如果要在OscsMall中使用这个界面，需要在MainActivity中启动Fragment为NewIndexDelegate。谢谢

