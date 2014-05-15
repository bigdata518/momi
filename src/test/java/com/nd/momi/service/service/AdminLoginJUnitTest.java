package com.nd.momi.service.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.nd.momi.service.localservice.ServiceLocalService;
import com.wolf.framework.utils.SecurityUtils;
import java.util.HashMap;
import java.util.Map;
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
        parameterMap.put("serviceId", "10000");
        parameterMap.put("password", SecurityUtils.encryptByMd5(ServiceLocalService.adminUserName));
        String result = this.testHandler.execute(ActionNames.ADMIN_LOGIN, parameterMap);
        System.out.println(result);
    }
}