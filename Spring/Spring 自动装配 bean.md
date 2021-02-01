@[toc]


------
# 一、搭建环境
环境就是：**一人一猫一狗**，人拥有一只猫和一只狗，狗和猫都会叫！

狗类：
```java
package com.wzq.pojo;

public class Dog {
    public void shout(){
        System.out.println("汪汪汪~");
    }
}
```
猫类：
```java
package com.wzq.pojo;

public class Cat {
    public void shout(){
        System.out.println("喵喵喵~");
    }
}
```
人的`pojo`类：
```java
package com.wzq.pojo;

public class Person {
    private String name;
    private Dog dog;
    private Cat cat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
```
`beans.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat" class="com.wzq.pojo.Cat" />
    <bean id="dog" class="com.wzq.pojo.Dog" />

    <bean id="person" class="com.wzq.pojo.Person" autowire="byName">
        <property name="name" value="wzq"/>
    </bean>

</beans>
```
测试类（先进行自动装配，然后再写这个类）：
```java
import com.wzq.pojo.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Person person = context.getBean("person", Person.class);
        person.getCat().shout();
        person.getDog().shout();
    }
}
```
# 二、byName
`byName`会自动在容器上下文中查找，和自己对象`set`方法后面的值相对应（**首字母小写**）对应的`bean 的 id`，语法格式很简单：
```xml
<bean id="id" class="全类型限定名" autowire="byName">
    
</bean>
```
只需要在`<bean>`标签，后面加个`autowire="byName"`即可，现在写一下完整的：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat" class="com.wzq.pojo.Cat" />
    <bean id="dog" class="com.wzq.pojo.Dog" />

    <bean id="person" class="com.wzq.pojo.Person" autowire="byName">
        <property name="name" value="wzq"/>
    </bean>

</beans>
```
运行测试类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201141952952.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
测试成功，如果上面的两个`bean`的名字不对应，那么就会报错：

错误的示范：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 首字母大写也不可以 -->
    <bean id="Cat" class="com.wzq.pojo.Cat" />
    <bean id="dog" class="com.wzq.pojo.Dog" />

    <bean id="person" class="com.wzq.pojo.Person" autowire="byName">
        <property name="name" value="wzq"/>
    </bean>

</beans>
```
运行测试类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201142112105.png)
成功报错！显然这种方式是有局限的
# 三、byType
`byType`会自动在容器上下文中查找，和自己对象属性类型相同的`bean`，它的语法格式是这样的：
```xml
<bean id="id" class="全类型限定名n" autowire="byType">
    
</bean>
```
只需要在`<bean>`标签，后面加个`autowire="byType"`即可，现在写一下完整的：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 这时候id不一样也没有关系 -->
    <bean id="Cat1" class="com.wzq.pojo.Cat" />
    <bean id="Dog1" class="com.wzq.pojo.Dog" />

    <bean id="person" class="com.wzq.pojo.Person" autowire="byType">
        <property name="name" value="wzq"/>
    </bean>

</beans>
```
运行测试类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201142456288.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
运行成功！如果出现多个同类型的`bean`呢？
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- 多个同类型bean -->
    <bean id="cat1" class="com.wzq.pojo.Cat" />
    <bean id="cat2" class="com.wzq.pojo.Cat" />
    <bean id="dog1" class="com.wzq.pojo.Dog" />
    <bean id="dog2" class="com.wzq.pojo.Dog" />

    <bean id="person" class="com.wzq.pojo.Person" autowire="byType">
        <property name="name" value="wzq"/>
    </bean>

</beans>
```
其实这时候，根本不能运行，因为在编译器中`bean`已经出现`红色波浪线`了：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201142724893.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
# 四、使用注解自动装配
使用注解实现自动装配需要在`.xml`文件中进行注册，需要引入约束和配置注解的支持：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

</beans>
```
来看看比原来的`.xml`多了什么：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201143538837.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
多了这么几个东西，现在就开始使用吧！

## 1、@Autowired
`@Autowired`直接在属性上使用即可，也可以在`set`方法上使用

使用`Autowired`，甚至可以不用编写`set`方法，但它的前提是这个自动装配的属性在`Ioc`容器中存在，且符合名字`byName`

`Person.class`：

```java
package com.wzq.pojo;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {
    private String name;
    @Autowired
    private Dog dog;
    @Autowired
    private Cat cat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //没有了set狗和猫的方法
    public Dog getDog() {
        return dog;
    }

    public Cat getCat() {
        return cat;
    }

}
```
`beans.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
    <bean id="dog" class="com.wzq.pojo.Dog" />
    <bean id="cat" class="com.wzq.pojo.Cat" />

    <bean id="person" class="com.wzq.pojo.Person">
        <property name="name" value="wzq" />
    </bean>

</beans>
```
运行测试类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201144330350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
如果`beans.xml`中，狗和猫的`bean`的`id`不一致，可以使用`@Qualifier(value = "xxx")`去指定一个唯一的`bean`对象注入

`Person.class`：
```java
package com.wzq.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Person {
    private String name;
    @Autowired
    @Qualifier(value = "dog1")
    private Dog dog;
    @Autowired
    @Qualifier(value = "cat1")
    private Cat cat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //没有了set狗和猫的方法
    public Dog getDog() {
        return dog;
    }

    public Cat getCat() {
        return cat;
    }
}
```
`beans.xml`：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
    
    <bean id="dog1" class="com.wzq.pojo.Dog" />
    <bean id="cat1" class="com.wzq.pojo.Cat" />

    <bean id="person" class="com.wzq.pojo.Person">
        <property name="name" value="wzq" />
    </bean>

</beans>
```
运行测试类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201144330350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
> 科普：`@Nullable` 这个注解说明这个字段可以设置为`null`
## 2、@Resource
`@Resource`是`java`的注解，它和`@Autowired`具有一模一样的功能，他们两个区别是:

- `@Autowired` 默认通过`byName`的方式实现，且必须要求这个对象存在
- `@Resource` 默认通过`byName`的方式实现，如果找不到名字，则通过`byType`实现，如果两个都找不到的情况下，就会报错

`Person.class`：

```java
package com.wzq.pojo;

import javax.annotation.Resource;

public class Person {
    private String name;
    @Resource(name = "dog1")
    private Dog dog;
    @Resource(name = "cat1")
    private Cat cat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //没有了set狗和猫的方法
    public Dog getDog() {
        return dog;
    }

    public Cat getCat() {
        return cat;
    }

}
```
`beans.xml`一样

运行测试类：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210201144330350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xlc2lsZXFpbg==,size_16,color_FFFFFF,t_70)
