@[toc]


----
# Tomcat 原来这么简单？
## 0、概述
`Tomcat`是什么？`Tomcat`是`Apache`开发的一款轻量级的免费的`Web应用服务器`，又叫`Web容器`。我们开发的JavaWeb项目都需要部署到这款软件上面，才能成功的被别人访问。

类似的`Web容器`还有`Jboss`、`WebLogic`等。

----

## 1、下载及安装
直接百度搜索`Tomcat`，然后打开`Apache Tomcat`官网，如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229142031180.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

直接点击进去，发现页面全都是英文的，直接看页面左侧的`Downland`，在该栏目下面有各种对应的版本，现在最新版本出到了`Tomcat 10测试版`，这里我下载的是`Tomcat 8`，直接点击`Tomcat 8`。如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229142259703.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

点开后如下图所示，如果你是`Windows 32位`系统，请点击`32-bit Windows zip`，即可下载。这里我是`Windiws 64位`系统，点击`64-bit Windows zip`，如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229142617946.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
下载之后会得到一个压缩包，直接解压，就算是下载安装成功了。需要注意的是：**Tomcat安装路径必须全英文**。我的路径是：`D:\apache-tomcat-8.5.61`

---

## 2、Tomcat目录结构
`Tomcat`的目录结构如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229143929802.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
现在分别看一下每个文件夹都是干什么的，如下图所示：

| 目录 | 作用 |
| -- | -- |
| `bin` | 存放可执行文件，例如`.bat（Windows执行）` 或`.sh（Linux执行）` |
| `conf` | 存放配置文件|
| `ib`| 存放依赖`jar包` |
| `logs` | 存储日志文件 |
| `temp` | 存放临时文件 |
| `webapps` | 存放web项目 |
| `work` | 存放运行时数据 |

---
## 3、如何启动
`Tomcat`的启动是很简单的，打开`bin`文件夹，找到`startup.bat`双击即可启动成功。启动成功如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229144648554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

如果遇到`闪退`或者其他`Tomcat`启动不成功的现象，[请点击我查看正确解决方法](https://blog.csdn.net/lesileqin/article/details/111874875)。


可能你打开这个之后，上面的汉字会乱码，就很恶心。如果想要汉字正常显示，你可以找到`conf\logging.properties`文件，编辑该文件，把里面所有的`UTF-8`改为`GBK`即可。

现在打开浏览器，访问网址：`http://localhost:8080/`，就会打开`Tomcat`默认显示的网页了。如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229145304150.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
在同一局域网其他电脑上输入`http://你主机的IP地址:8080`，也可以访问到该网页。

---

## 4、如何正确关闭
看到这里你可能会吐槽：如何关闭还要你教我？我直接点错号不就关闭了吗。

是的是的，这样的确可以关闭`Tomcat`，但这并不是正确的关闭方式。`Tomcat`关闭之后还要进行一系列的操作（例如保存日志等），如果点击错号直接关闭，这些后续操作就直接停掉了，就好比你的电脑电源断了！所以正确的关闭`Tomcat`是很有必要的。

正确关闭`Tomcat`有两种方式：

- 双击`bin`目录下的`shutdown.bat`
- 在刚刚打开的小黑框框里面直接按下`Ctrl+C`

---

## 5、部署web项目的三种方式
现在我们准备一个简单的静态网页，并把它放到一个文件夹中，作为测试项目。

这里我新建了一个文件夹`hello`，在此文件夹中，新建了一个`hello.html`：

```html
<h1 style="color:red">Hello , Tomcat!</h1>
```
### 1）直接拷贝 【不推荐】
打开`bin/startup.bat`。直接拷贝这个上面的那个项目到`webapps`目录下，即可。这时候，我们打开浏览器，输入：`http://localhos:8080/hello/hello.html`便可成功显示。如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229151236702.png#pic_center)
也可以压缩我们刚刚的那个文件夹为`.zip`格式，直接更改后缀名为`.war`再把这个`hello.war`放到`webapps`目录下。这时候`Tomcat`会自动帮我们解压缩，生成`hello`文件夹（需要等一下），如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229151455407.png#pic_center)
这就算是部署成功了，但是这种部署方式不好，有时候我们的项目会很大，用这种方式复制可能会浪费很多时间。所以并不推荐这种部署方式。

### 2）配置conf/server.xml文件 【不推荐】
这种部署方式不需要我们拷贝文件到`webapps`目录，而是修改配置文件`conf/server.xml`，编辑该`.xml`文件，找到`<Host></Host>`标签。如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201229152218812.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
直接在上图画红色框子的位置，添加标签`<Context />`，格式如下所示：

```xml
<Context docBase="项目存放路径" path="/定义虚拟目录" />
```
其中虚拟目录的意思就是输入网址的时候，找到该文件夹的一个目录。比如，我的配置为：
```xml
<Context docBase="D:\Users\wzq\Desktop\hello" path="/hh" />
```
点击保存，重启`Tomcat`，打开浏览器，输入：`http://localhost:8080/hh/hello.html`，即可成功访问。

但是这种配置方式不太安全，因为如果我们每次部署的话都要要`conf/server.xml`里面添加标签，`conf/server.xml`如果配置错误，后果是不堪想象的。所以这种配置方式也不推荐。


### 3）热部署 【推荐】
打开`conf/Catalina/localhost/`，在这里新建一个`名称.xml`文件，编辑该文件，写入标签：

```xml
<Context docBase="项目存放路径" />
```

注意这里不需要写`path`了，因为我们现在的虚拟目录就是刚刚创建文件的`xml`文件的名称。比如：我在`conf/Catalina/localhost/`目录下创建一个`hh.xml`，写入以下代码：

```xml
<Context docBase="D:\Users\wzq\Desktop\hello"/>
```
点击保存，重启`Tomcat`，打开浏览器，输入：`http://localhost:8080/hh/hello.html`，即可成功访问。

这种方式是我们推荐的`热部署`。
