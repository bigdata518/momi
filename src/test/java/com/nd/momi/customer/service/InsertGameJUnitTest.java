/**
 * GAME_LIST 游戏显示接口测试
 */
package com.nd.momi.customer.service;

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
 * @author cy
 *
 */
public class InsertGameJUnitTest extends AbstractMomiTest {

    public InsertGameJUnitTest() {
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
        this.setCustomerSession("1");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("gameName", "战");
        Response response = this.testHandler.execute(ActionNames.INSERT_GAME,parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
    }
}
