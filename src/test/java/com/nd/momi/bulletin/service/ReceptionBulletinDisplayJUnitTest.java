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
public class ReceptionBulletinDisplayJUnitTest extends AbstractMomiTest {

    public ReceptionBulletinDisplayJUnitTest() {
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
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        Response response = this.testHandler.execute(ActionNames.RECEPTION_BULLETIN_DISPLAY, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }


}
