package com.nd.momi.bulletin.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.config.DefaultResponseFlags;
import com.wolf.framework.worker.context.Response;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lgf on 2014/5/21.
 */
public class DeleteBulletinJUnitTest extends AbstractMomiTest {

    public DeleteBulletinJUnitTest() {
    }

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {


    }

    //@Test
    public void testReception(){
        this.setReceptionSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("bulletinType", "RECEPTION");
        Response response = this.testHandler.execute(ActionNames.DELETE_BULLETIN, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }

   // @Test
    public void testCustomer(){
        this.setReceptionSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(1, 1);
        parameterMap.put("bulletinType", "CUSTOMER");
        Response response = this.testHandler.execute(ActionNames.DELETE_BULLETIN, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}
