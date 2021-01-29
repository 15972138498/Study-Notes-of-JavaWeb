import com.wzq.dao.UserDaoMySqlImpl;
import com.wzq.service.UserService;
import com.wzq.service.UserServiceImpl;
import org.junit.Test;

public class MyTest {

    @Test
    public void Test(){
        UserService service = new UserServiceImpl();
        ((UserServiceImpl) service).setUserDao(new UserDaoMySqlImpl());
        service.getUser();
    }

}
