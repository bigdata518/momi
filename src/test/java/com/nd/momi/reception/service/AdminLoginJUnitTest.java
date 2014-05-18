package com.nd.momi.reception.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.wolf.framework.config.DefaultResponseFlags;
import com.wolf.framework.utils.SecurityUtils;
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
public class AdminLoginJUnitTest extends AbstractMomiTest {

    public AdminLoginJUnitTest() {
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
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("receptionId", "10000");
        parameterMap.put("password", SecurityUtils.encryptByMd5(ReceptionLocalService.adminUserName));
        Response response = this.testHandler.execute(ActionNames.ADMIN_LOGIN, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}