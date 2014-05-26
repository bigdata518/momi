package com.nd.momi.message.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.config.DefaultResponseFlags;
import com.wolf.framework.worker.context.Response;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
public class SendMessageFromCustomerJUnitTest extends AbstractMomiTest {

    public SendMessageFromCustomerJUnitTest() {
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
        this.setCustomerSession("1158174740");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("receptionId", "271411");
        parameterMap.put("message", "hello 271411");
        Response response = this.testHandler.execute(ActionNames.SEND_MESSAGE_FROM_CUSTOMER, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}