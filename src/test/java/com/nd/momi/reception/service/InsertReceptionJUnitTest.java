package com.nd.momi.reception.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.config.DefaultResponseFlags;
import com.wolf.framework.worker.context.Response;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class InsertReceptionJUnitTest extends AbstractMomiTest {

    public InsertReceptionJUnitTest() {
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
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("receptionId", "707909");
        parameterMap.put("receptionName", "guifang");
        parameterMap.put("type", "MEMBER");
        Response response = this.testHandler.execute(ActionNames.INSERT_RECEPTION, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}