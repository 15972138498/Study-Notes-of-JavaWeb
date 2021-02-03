import com.wzq.dao.UserMapper;
import com.wzq.dao.UserMapperImpl;
import com.wzq.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.showAllUser()) {
            System.out.println(user);
        }
    }
}
