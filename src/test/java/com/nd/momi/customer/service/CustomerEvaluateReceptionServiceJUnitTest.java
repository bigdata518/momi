/**
 * CUSTOMER_EVALUATE_RECEPTION 客户评价客服接口测试
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
public class CustomerEvaluateReceptionServiceJUnitTest extends AbstractMomiTest {

    public CustomerEvaluateReceptionServiceJUnitTest() {
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
        //测试案例1,正常案例
        this.setCustomerSession("1158174740");
        Map<String, String> parameterMap = new HashMap<String, String>(6, 1);
        parameterMap.put("customerId", "1158174740");
        parameterMap.put("receptionId", "2258174740");
        parameterMap.put("score", "1");
//        parameterMap.put("receptionQuality", "质量一般");
//        parameterMap.put("problemSolve", "问题已解决");
//        parameterMap.put("suggestion", "希望态度能更好");
        Response response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);
        //测试案例2，score大于3为不合法数据
        this.setCustomerSession("10");
        Map<String, String> parameterMap1 = new HashMap<String, String>(6, 1);
        parameterMap1.put("customerId", "10");
        parameterMap1.put("receptionId", "2258174740");
        parameterMap1.put("score", "10");
        parameterMap1.put("receptionQuality", "质量一般");
        parameterMap1.put("problemSolve", "问题已解决");
        parameterMap1.put("suggestion", "希望态度能更好");
        response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap1);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), "FAILURE_ERROR_DATA");

    }
}