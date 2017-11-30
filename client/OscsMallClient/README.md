> 这个项目是我一年多的安卓学习总结，里面很多地方都是通过学习别人的写法仿照和扩展的

# OscsMall的安卓客户端
MVP+快速开发框架

## CreamSoda核心Module
> 一个快速开发的框架

* 基础配置
* 网络请求


> fast-dev内置多种解决方案的module
NormalLauncherDelegate:普遍的闪屏方案，一个倒计时，一个IconifyTextView，一个标语TextView
继承NormalLauncherDelegate，实现`iconTextOrResource()`,`sloganText()`,