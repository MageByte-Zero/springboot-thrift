package testDemo;

import com.zero.Application;
import com.zero.client.AsynService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AsyncTest {

    @Autowired
    private AsynService asynService;

    @Test
    public void testAsynExecute() {
        for(int i =0 ;i<10;i++){
            asynService.executeAsyncTask(i);
            asynService.executeAsyncTaskPlus(i);
        }
    }
}
