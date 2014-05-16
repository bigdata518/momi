package com.nd.momi.reception.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class ServiceLogoutJUnitTest extends AbstractMomiTest {

    public ServiceLogoutJUnitTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void test() {
        this.setReceptionSession("271411");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        String result = this.testHandler.execute(ActionNames.RECEPTION_LOGOUT, parameterMap);
        System.out.println(result);
    }
}