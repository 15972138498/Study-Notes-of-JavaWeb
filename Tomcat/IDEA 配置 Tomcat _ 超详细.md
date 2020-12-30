首先需要安装`Tomcat`，[点我查看`Tomcat`的下载与安装以及部署](https://blog.csdn.net/lesileqin/article/details/111907740)。

打开`IDEA`，打开之后是这样的：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230104356277.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
点击右上角的红色框子`Add Configurations`，会弹出如下图所示的界面：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020123010452641.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

然后点击那个`+`号，朝下拉，找到`Tomcat Server`，点击一下，然后点击`;Local`，如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230104619386.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

点击之后，是这样的：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230104800628.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)

如果是第一次配置，那你的红色框子部分应该是空白的，点一下框子旁边的`Configure`，点击如下图所示的`文件夹图标`，选择`Tomcat`的安装位置，即可。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230105038263.png#pic_center)

然后点个`OK`，回到刚刚的那个页面，点击`Deployment`，然后点击`+`，把你的项目添加进去。点击`+`之后会出现两个选项，第一个是`Artifact`这个是把当前在`IDEA`中的项目添加进去的，第二个是`Extrenal Source`这表示添加别的项目，选择路径就好了。如下图所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230105228789.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70#pic_center)
弄完之后点`OK`，创建一个项目，点击运行即可。







