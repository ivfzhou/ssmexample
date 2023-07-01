package cn.ivfzhou.ssmexample;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml"})
public class SpringTests {

    @Test
    public void test() {
        System.out.println("yes");
    }

}
