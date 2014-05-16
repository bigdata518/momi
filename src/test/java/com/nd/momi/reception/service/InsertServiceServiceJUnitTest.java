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
public class InsertServiceServiceJUnitTest extends AbstractMomiTest {

    public InsertServiceServiceJUnitTest() {
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
        this.setReceptionSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("receptionId", "271411");
        parameterMap.put("receptionName", "jianying");
        parameterMap.put("type", "MEMBER");
        String result = this.testHandler.execute(ActionNames.INSERT_RECEPTION, parameterMap);
        System.out.println(result);
    }
}