package com.nd.momi.bulletin.service;

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
 * Created by lgf on 2014/5/21.
 */
public class UpdateBulletinJUnitTest extends AbstractMomiTest {

    public UpdateBulletinJUnitTest() {
    }

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testReception(){
        this.setReceptionSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("bulletinType", "RECEPTION");
        parameterMap.put("title", "客服公告");
        parameterMap.put("content", "测试公告内容");
        Response response = this.testHandler.execute(ActionNames.UPDATE_BULLETIN, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }

    @Test
    public void testCustomer(){
        this.setReceptionSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("bulletinType", "CUSTOMER");
        parameterMap.put("title", "客户公告");
        parameterMap.put("content", "测试公告内容");
        Response response = this.testHandler.execute(ActionNames.UPDATE_BULLETIN, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}
