@[toc]

----
# 一、下载
`Maven`的下载是很简单的，直接到百度搜索`Maven`，进入`Maven`官网，如下图红色框子所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118192401703.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
点击进入官网或者[直接点我](http://maven.apache.org/)，点击左侧的`Downland`：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118192728433.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
然后点击如下图所示的`apache-maven-3.6.3-bin.zip`，这是下载`maven`压缩包的
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118192840906.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)

下载完成之后，解压到`D盘`（**路径必须全英文！**），此时文件目录应该是这样子的：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118193043972.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
至此，`Maven`已经安装好了！

# 二、配置环境变量
现在配置环境变量，和配置`Java`的环境一样，首先`右键此电脑`，点击`属性`，然后点击`高级系统设置`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118193411431.png)
点击进去之后，先点`高级`，再点`环境变量`：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118193458823.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
然后点击下面的框子`系统变量`，有个`新建`，点击一下，又会弹出一个对话框：
![在这里插入图片描述](https://img-blog.csdnimg.cn/202101181937465.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118193814571.png)
变量名填：`M2_HOME`

变量值填：你的`Maven`的安装位置的bin目录，比如：`D:\apache-maven-3.6.3\bin`

然后同样的步骤，`新建系统变量`

变量名填：`MAVEN_HOME`

变量值为：`Maven`的安装位置，比如：`D:\apache-maven-3.6.3`，如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2021011819404087.png)
此刻系统变量框子里面应该新建好了两个变量，如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118194133579.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
然后，在这个框子里面找到`Path`，点击编辑：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118194234859.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
点击`编辑`之后，新建，输入`%MAVEN_HOME%\bin`，如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118194318240.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
至此环境变量配置成功，现在可以打开`cmd`，输入`mvn -version`，看一下:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118194359755.png)
如果出现版本信息，则配置成功。==如果按照上述步骤配置不成功的，可以到评论区留言，我看到之后会第一时间回答（回答我会的）==

# 三、配置阿里云镜像
找到`Maven`安装目录，有一个`conf`文件夹，该文件夹里面有`setting.xml`文件，右键点击编辑。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118194657388.png)
直接按`Ctrl+F`，输入`mirrors`，找到该标签，
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118194831825.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
在该标签体内，添加如下代码：

```xml
 	<mirror>
		<id>nexus-aliyun</id>
		<mirrorOf>central</mirrorOf>
		<name>Nexus aliyun</name>
		<url>http://maven.aliyun.com/nexus/content/groups/public</url>
	 </mirror>
```
阿里云镜像配置成功！
# 四、本地仓库
还是在`setting.xml`文件中，按下`Ctrl+F`，输入`localRepository`，找到这个东西

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118195019438.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
在上述空框子的地方填如以下代码：

```xml
<localRepository>D:\apache-maven-3.6.3\maven-repo</localRepository>
```
然后回到`Maven`安装目录，创建一个名为`maven-repo`的文件夹：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210118195205879.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
本地仓库配置成功！
