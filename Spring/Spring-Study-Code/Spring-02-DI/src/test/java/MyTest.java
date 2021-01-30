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

        /*
        Student{
            name='wzq',
            address=Address{address='洛阳'},
            books=[红楼梦, 西游记, 水浒传, 三国演义],
            hobbys=[听歌, 敲代码, 看电影],
            card={身份证=41032xxxxxxxxxxxxx, 银行卡=41032xxxxxxxxxxxxx},
            games=[英雄联盟, 王者荣耀, 吃鸡],
            wife='null',
            info={password=password, url=url, driver=driver, username=username}}
        * */
    }
}
