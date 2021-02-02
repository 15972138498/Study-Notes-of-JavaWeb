import com.wzq.config.BeansConfig;
import com.wzq.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {
    @Test
    public void Test(){
        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
        User user = context.getBean("user111", User.class);
        user.getDog().shout();
        System.out.println(user);
    }
}
