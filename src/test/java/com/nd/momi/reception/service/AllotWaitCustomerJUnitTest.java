package com.nd.momi.reception.service;

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
public class AllotWaitCustomerJUnitTest extends AbstractMomiTest {

    public AllotWaitCustomerJUnitTest() {
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
        Map<String, String> parameterMap = new HashMap<String, String>();
        Response response = this.testHandler.execute(ActionNames.ALLOT_WAIT_CUSTOMER, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}