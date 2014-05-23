/**
 * 玩家正常关闭接口 CUSTOMER_FINISH_DIALOGUE 测试
 */
package com.nd.momi.dialogue.service;

import com.nd.momi.AbstractMomiTest;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.config.DefaultResponseFlags;
import com.wolf.framework.worker.context.Response;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author cy
 *
 */

public class CustomerFinishDialogueJUnitTest extends AbstractMomiTest {

    public CustomerFinishDialogueJUnitTest() {
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
        parameterMap.put("customerId", "1158174740");
        parameterMap.put("receptionId", "2258174740");
        Response response = this.testHandler.execute(ActionNames.CUSTOMER_FINISH_DIALOGUE, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}