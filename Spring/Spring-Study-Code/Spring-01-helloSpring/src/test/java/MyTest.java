import com.wzq.pojo.hello;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        //获取Spring Context对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //对象都在Spring中管理，要使用可以直接拿
        hello hello =(hello) context.getBean("hello");
        System.out.println(hello);

    }
}
