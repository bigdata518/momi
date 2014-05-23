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
public class CustomerListJUnitTest extends AbstractMomiTest {

    public CustomerListJUnitTest() {
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
        this.setReceptionSession("2258174740");
        Map<String, String> parameterMap = new HashMap<String, String>(1, 1);
        Response response = this.testHandler.execute(ActionNames.CUSTOMER_LIST, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}