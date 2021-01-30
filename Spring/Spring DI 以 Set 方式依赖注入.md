@[toc]

----
# 一、搭建环境
打开`IDEA`创建不使用骨架的`Maven`工程，然后打开`pim.xml`，导入`SpringFreamwork`与`Junit`：
```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/junit/junit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.12.RELEASE</version>
    </dependency>
</dependencies>
```
创建一个包含所有类型的实体类：

创建`Address`实体类，作为另一个实体类的属性用：
```java
package com.wzq.pojo;

public class Address {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}
```
创建`Student`类：
```java
package com.wzq.pojo;

import java.util.*;

public class Student {
	//属性：
    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbys;
    private Map<String,String> card;
    private Set<String> games;
    private String wife;    //这个用来注入空，毕竟没有媳妇儿
    private Properties info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }

    public Map<String, String> getCard() {
        return card;
    }

    public void setCard(Map<String, String> card) {
        this.card = card;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public String getWife() {
        return wife;
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", books=" + Arrays.toString(books) +
                ", hobbys=" + hobbys +
                ", card=" + card +
                ", games=" + games +
                ", wife='" + wife + '\'' +
                ", info=" + info +
                '}';
    }
}
```
创建`beans.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="com.wzq.pojo.Address">
        <property name="address" value="洛阳"/>
    </bean>
</beans>
```
# 二、使用Set方式注入
## 1、实现依赖注入
打开`beans.xml`（说明都在注释里）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="com.wzq.pojo.Address">
        <property name="address" value="洛阳"/>
    </bean>

    <bean id="student" class="com.wzq.pojo.Student">
        <!-- 默认方式 -->
        <property name="name" value="wzq"/>
        <!-- 对象 -->
        <property name="address" ref="address"/>
        <!-- 数组 -->
        <property name="books">
            <array>
                <value>红楼梦</value>
                <value>西游记</value>
                <value>水浒传</value>
                <value>三国演义</value>
            </array>
        </property>
        <!-- list -->
        <property name="hobbys">
            <list>
                <value>听歌</value>
                <value>敲代码</value>
                <value>看电影</value>
            </list>
        </property>
        <!-- Map -->
        <property name="card">
            <map>
                <entry key="身份证" value="41032xxxxxxxxxxxxx"/>
                <entry key="银行卡" value="41032xxxxxxxxxxxxx"/>
            </map>
        </property>
        <!-- Set -->
        <property name="games">
            <set>
                <value>英雄联盟</value>
                <value>王者荣耀</value>
                <value>吃鸡</value>
            </set>
        </property>
        <!-- null -->
        <property name="wife">
            <null/>
        </property>
        <!-- properties -->
        <property name="info">
            <props>
                <prop key="driver">driver</prop>
                <prop key="url">url</prop>
                <prop key="username">username</prop>
                <prop key="password">password</prop>
            </props>
        </property>
    </bean>

</beans>
```
## 2、测试
使用`Junti`测试：
```java
import com.wzq.pojo.Student;
import com.wzq.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = context.getBean("student", Student.class);
        System.out.println(student.toString());
    }
}
```
输出结果：
```java
Student{
    name='wzq',
    address=Address{address='洛阳'},
    books=[红楼梦, 西游记, 水浒传, 三国演义],
    hobbys=[听歌, 敲代码, 看电影],
    card={身份证=41032xxxxxxxxxxxxx, 银行卡=41032xxxxxxxxxxxxx},
    games=[英雄联盟, 王者荣耀, 吃鸡],
    wife='null',
    info={password=password, url=url, driver=driver, username=username}
}
```
