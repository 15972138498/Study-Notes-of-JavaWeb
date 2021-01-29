**暂且不看`Ioc`**

按照以前开发的套路，先写一个小例子：

新建一个项目，创建`com.wzq.dao`包，写一个`UserDao`接口：
```java
package com.wzq.dao;

public interface UserDao {
    void getUser();
}
```
在此包下新建一个默认的实现类，输出一句话
```java
package com.wzq.dao;

public class UserDaoImpl implements UserDao{
    public void getUser() {
        System.out.println("正在使用默认方式读取User...");
    }
}
```
现在创建`com.wzq.service`包，写一个接口：
```java
package com.wzq.service;

public interface UserService {
    void getUser();
}
```
新建实现类：
```java
package com.wzq.service;

import com.wzq.dao.UserDao;
import com.wzq.dao.UserDaoImpl;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    
    public void getUser() {
        dao.getUser();
    }
}
```
测试：
```java
import com.wzq.service.UserService;
import com.wzq.service.UserServiceImpl;
import org.junit.Test;

public class MyTest {
    @Test
    public void Test1(){
        UserService service = new UserServiceImpl();
        service.getUser();
    }
}
```
输出结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129202657389.png)
如果现在用户不想用`默认方式获取数据`，想用`MySql`获取数据咋办？按照以前的套路肯定是再创建一个`MySql`关于`UserDao`接口的实现类喽：
```java
package com.wzq.dao;

public class UserDaoMySqlImpl implements UserDao {
    public void getUser() {
        System.out.println("正在使用MySQL读取User...");
    }
}
```
然后再在`UserServiceImpl`实现类中更改获取的对象，最后测试：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129202955469.png)
成功了，那如果说：用户现在想用`Sql Server`呢，继续加呗，继续改`service`呗，那如果用户想用`oracle`呢？继续加呗，继续改`service`呗……那如果用户想…………………………好了打死用户吧！！！

总而言之，用这样的方式，每次都需要修改`service`层的代码，好麻烦，所以为了解决这个问题，可以在`UserServiceImpl`中加一个`set`方法：
```java
//利用set进行动态实现值的注入！
public void setUserDao(UserDao dao) {
    this.dao = dao;
}
```
现在测试的时候，只需要调用这个方法传递想要用的`dao`层实现类即可：
```java
@Test
public void Test1(){
    UserService service = new UserServiceImpl();
    ((UserServiceImpl) service).setUserDao(new UserDaoMySqlImpl());
    service.getUser();
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210129203711963.png)
这就是控制反转`Ioc`的思想！通过在`service`层设置一个`set`方法，就可以让程序的主动权从`程序员`手中移交到`用户`手中，用户想用什么就传什么参数就好了！
