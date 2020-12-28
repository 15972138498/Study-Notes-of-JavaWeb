[toc]

---
# Tomcat常见问题
## 1、点击startup.bat出现闪退
出现闪退的原因是：`JDK`没有正确配置，所以只要正确配置`JDK`就好啦！	
### 1）解决办法1 — 正确配置JDK
很多人，都会像下图这样配置`JDK`，但是这种配置方式不是标准的。
![图1—错误的JDK配置方式](https://img-blog.csdnimg.cn/20201228210617970.png#pic_center)
正确的`JDK`配置如下：

依次：右键此电脑、属性、高级系统设置、高级、系统变量，点到下图为止：

![按照上述步骤点击到这一步骤](https://img-blog.csdnimg.cn/20201228214204648.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
点击上图中的`新建`，会出现类似下面的对话框。

![点击新建后出现新建系统变量对话框](https://img-blog.csdnimg.cn/20201228214256208.png#pic_center)
设置变量名为：`JAVA_HOME`，然后点击下方的`浏览目录`，选择到你的`JDK`安装路径，我的`JDK`路径是：`D:\Program Files\Java\jdk1.8.0_131`。设置好之后，如下图所示：

![按照规则配置系统变量](https://img-blog.csdnimg.cn/2020122821443891.png#pic_center)
接下来，继续点击上面的那个`新建`。其中变量名为：`CLASSPATH`，变量值写为：

`.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar`

**注意到！在最前面有个`点`！！** 配置完成之后，应如下图所示：

![按照规则配置第二个系统变量](https://img-blog.csdnimg.cn/20201228214844673.png#pic_center)
最后一步，找到`环境变量`对话框下方的这个框子的`Path`，然后点击`编辑`：
![配置Path](https://img-blog.csdnimg.cn/20201228215728417.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
这时候会弹出一个对话框，接着点击`新建`，然后输入：`%JAVA_HOME%\bin`，如下图所示！

![](https://img-blog.csdnimg.cn/20201228215856663.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
至此，全部配置完毕，可以先打开`cmd`，测试`JDK`是否安装成功，最后点击`Tomcat\bin\startup.bat`测试。

### 2）解决办法2 — 修改配置文件
第二钟解决方法比较简单，直接`右键单击编辑startup.bat与shutdown.bat`，在该文本最前面输入：

```
SET JAVA_HOME=D:\Program Files\Java\jdk1.8.0_131
SET TOMCAT_HOME=D:\apache-tomcat-8.5.61
```
注意：`JAVA_HOME`与`TOMCAT_HOME`后面的路径均为两个软件所在位置。

---
## 2、启动报错
启动报错的原因可能是：已经有一个进程占有了`8080端口`。（`8080端口`是`Tomcat`的默认端口），那么我们很明显的有两种解决方案，一种是杀死占用`8080端口`的进程；另一种方法是修改`Tomcat`自身的端口。
### 1）暴力解决 — 杀死进程
打开`cmd`输入：`netstat -ano`查看所有进程的端口号与他们的`PID`，找到对应占用`8080端口`对应进程的`PID`，如下图所示：

![查看端口号对应的进程](https://img-blog.csdnimg.cn/20201228221024436.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
然后打开`任务管理器`，点击`详细信息`，找到对应进程号，右键点击`结束进程`即可。

### 2）温柔解决 — 修改tomcat自身端口号
修改`tomcat`自身的端口号，首先打开`tomcat`目录下的`conf\server.xml`，找到`Connector`标签，如下所示：

```xml
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```
只需要把`port`后面的端口号改为其他值就行，但是这个值的取值范围在`0-65535`之间，至于为什么要在这个区间，[请点我查看`计算机网络`相关知识](https://blog.csdn.net/lesileqin/article/details/109469286)。

建议修改`portt="80"`，因为`80端口`是`HTTP协议`默认端口号，修改为`80`的优点是：访问`Tomcat`服务器的时候可以省略最后面的端口号不写。