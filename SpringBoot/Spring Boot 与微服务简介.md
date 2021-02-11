`@[toc]


----
# 一、Spring Boot简介
`Spring Boot`是`Pivotal`团队提供的全新框架，其目的是为了简化`Spring`应用的初始搭建和开发过程的，该框架使用了特定的方式（`约定大于配置`）来进行配置，从而使开发人员不再需要定义样板化的配置。

以前做`Java Web`应用，开发一个`Web`应用，从部署`Tomcat`服务器到`Servlet`，最后跑出一个`Hello World`程序，要经历超级超级多的步骤，后来出现了`Spring`框架，然后是`Spring MVC`，接着又有了`Spring Boot`，`Spring Boot`就是一个`javaWeb`的开发框架，它的主要特点是`约定大于配置`，能够迅速的开发`Web`应用

`Spring Boot`是基于`Spring4.0`开发的，它本身并不提供`Spring`框架的核心特性以及扩展功能，只是用于快速、敏捷地开发新一代基于`Spring`框架的应用程序，它不是用来替代`Spring`的解决方案，而是和`Spring`框架紧密结合用于提升`Spring`开发者体验的工具。

`Spring Boot`的核心思想是：==约定大于配置==，它并不是什么新的框架，它默认帮我们进行了很多的设置，就像`maven`整合了所有的`jar`包，`Spring Boot`整合了所有的框架
# 二、Spring Boot的优点
- 为所有`Spring`开发者更快的入门
- 开箱即用，提供各种默认配置来简化项目配置
- 内嵌式容器简化`Web`项目
- 没有冗余代码生成和`XML`配置的要求

# 三、微服务架构
微服务是一种架构风格，它要求我们在开发一个应用的时候，这个应用必须构架成一系列小服务的组合；可以通过`Http`的方式进行互通。

## 1、单体应用架构
单体应用架构（`all`）是早期`web`开发所用的架构，这种架构把一个应用中的所有服务都封装到一个应用中，随着网站的流量越来越大，单个服务器承载不住用户访问的压力，所以就把这个部署到多个服务器中。看下图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211212054850.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
这样做也有自己的好处，那就是易于开发和测试，也十分方便部署，当需要扩展的时候，只需要把`war`包复制多份，放到多个服务器上，再做个负载均衡就可以了

但是他也有自己明显的缺点，哪怕要修改一个非常小的地方，就需要停掉所有的服务，重新打包，部署这个应用，如果对于一个大型应用，我们不可能把所有内容都放在一个应用里面，所以缺陷也非常突出
## 2、微服务架构
`all in one`的架构方式有这么明显的缺点，所以就出来了微服务架构，微服务架构打破了`all in one`的架构方式，把每个功能元素独立出来，把独立出来的功能元素动态组合，需要的功能元素才去拿来组合，需要多一些时可以整合多个功能元素，所以微服务架构是对功能元素进行复制，而没有对整个应用进行复制，如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211212655835.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
这样做的好处是：

- 节省了调用资源
- 每个功能元素的服务都是一个可替换的、可独立升级的软件代码

## 3、如何构架微服务
一个大型系统的微服务架构，就像一个复杂交织的神经网络，每一个神经元就是一个功能元素，它们各自完成自己的功能，然后通过`Http`相互请求调用，比如：浏览、结账、支付等服务都是一个个独立的功能服务，都被微化了，他们作为一个个微服务，共同构建了一个庞大的系统，如果修改其中一个功能，只需要更新升级其中一个功能服务单元即可

但是这种庞大的系统架构给部署和运维带来很大的难度，于是`Spring`为我们带来了构建大型分布式微服务的全套、全程产品：

- 构建一个个功能独立的微服务应用单元，可以使用`Spring Boot`，它可以帮我们迅速构建一个应用
- 大型分布式网络服务的调用，这部分由`Spring Cloud`来完成，实现分布式
- 在分布式中间，进行流式数据计算、批处理，用`Spring cloud data flow`
- `Spring`为我们想清楚了整个从开始搭建应用到大型分布式应用全流程方案

# 四、Spring Boot 与 Spring Cloud学习思维导图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210211213755869.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
