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
 * Created by Administrator on 2014/5/21.
 */
public class InquireBulletinJUnitTest extends AbstractMomiTest {

    public InquireBulletinJUnitTest() {
    }

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void test(){
        this.setReceptionSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        Response response = this.testHandler.execute(ActionNames.INQUIRE_BULLETIN, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }


}
