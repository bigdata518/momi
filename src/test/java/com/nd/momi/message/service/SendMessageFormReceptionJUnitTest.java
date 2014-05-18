package com.nd.momi.message.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.config.DefaultResponseFlags;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
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
public class SendMessageFormReceptionJUnitTest extends AbstractMomiTest {

    public SendMessageFormReceptionJUnitTest() {
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
        Session session = new SessionImpl("1158174740");
        this.testHandler.setSession(session);
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("customerId", "1158174740");
        parameterMap.put("message", "hello 1158174740");
        Response response = this.testHandler.execute(ActionNames.SEND_MESSAGE_FROM_RECEPTION, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}