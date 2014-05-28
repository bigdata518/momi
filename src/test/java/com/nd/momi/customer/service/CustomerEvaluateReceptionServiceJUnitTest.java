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
        this.setCustomerSession("1158164740");
        Map<String, String> parameterMap = new HashMap<String, String>(6, 1);
        parameterMap.put("receptionId", "23");
        parameterMap.put("score", "1");
        parameterMap.put("receptionQuality", "质量一般");
        parameterMap.put("problemSolve", "问题已解决");
        parameterMap.put("suggestion", "希望态度能更好");
        Response response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);

        //测试案例2，score大于3为不合法数据
        this.setCustomerSession("11581743");
        Map<String, String> parameterMap1 = new HashMap<String, String>(6, 1);
        parameterMap1.put("receptionId", "23");
        parameterMap1.put("score", "5");
        parameterMap1.put("receptionQuality", "质量一般");
        parameterMap1.put("problemSolve", "问题已解决");
        parameterMap1.put("suggestion", "希望态度能更好");
        response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap1);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), "FAILURE_ERROR_DATA");

        //测试案例3,score=1,receptionQuality,suggestion=null
        this.setCustomerSession("1258174740");
        Map<String, String> parameterMap2 = new HashMap<String, String>(6, 1);
        parameterMap2.put("receptionId", "24");
        parameterMap2.put("score", "1");
        parameterMap2.put("suggestion", "希望态度能更好");
        response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap2);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), "FAILURE_ERROR_DATA");

        //测试案列4，score为负
        this.setCustomerSession("1158374740");
        Map<String, String> parameterMap3 = new HashMap<String, String>(6, 1);
        parameterMap3.put("receptionId", "25");
        parameterMap3.put("score", "-1");
        parameterMap1.put("receptionQuality", "质量一般");
        parameterMap1.put("problemSolve", "问题已解决");
        parameterMap3.put("suggestion", "希望态度能更好");
        response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap3);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), "FAILURE_ERROR_DATA");

        //测试案例5,正常案例
        this.setCustomerSession("1158164740");
        Map<String, String> parameterMap4 = new HashMap<String, String>(6, 1);
        parameterMap4.put("receptionId", "22");
        parameterMap4.put("score", "1");
        parameterMap4.put("receptionQuality", "质量一般");
        parameterMap4.put("problemSolve", "问题已解决");
        response = this.testHandler.execute(ActionNames.CUSTOMER_EVALUATE_RECEPTION, parameterMap4);
        System.out.println(response.getResponseMessage());
        Assert.assertEquals(response.getFlag(), DefaultResponseFlags.SUCCESS);


    }
}