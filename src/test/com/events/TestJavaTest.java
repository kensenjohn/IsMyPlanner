package test.com.events;

import com.events.TestJava;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/9/13
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJavaTest {
    @Test
    public void testSum() throws Exception {
        TestJava testJava = new TestJava();
        Assert.assertEquals("5 + 6 = 11" , new Integer(11), testJava.sum(5,6));
    }
}
