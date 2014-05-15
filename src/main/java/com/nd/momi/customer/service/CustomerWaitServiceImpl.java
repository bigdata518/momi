package com.nd.momi.customer.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.localservice.CustomerLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_WAIT,
        responseConfigs = {
    @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @ResponseConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "昵称"),
    @ResponseConfig(name = "waitNum", typeEnum = TypeEnum.LONG, desc = "等待人数"),
    @ResponseConfig(name = "waitOrder", typeEnum = TypeEnum.LONG, desc = "排队序号")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "加入等待队列")
public class CustomerWaitServiceImpl implements Service {
    
    @InjectLocalService()
    private CustomerLocalService customerLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerUserIdFromSessionId(sid);
        CustomerEntity customerEntity = this.customerLocalService.inquireCustomerById(customerId);
        String customerName = customerEntity.getCustomerName();
        long waitNum = this.customerLocalService.countWaitCustomerNum();
        String waitOrder = Long.toString(System.currentTimeMillis());
        this.customerLocalService.insertWaitCustomer(customerId, customerName, waitOrder);
        Map<String, String> resultMap = new HashMap<String, String>(4, 1);
        resultMap.put("customerId", customerId);
        resultMap.put("nickName", customerName);
        resultMap.put("waitNum", Long.toString(waitNum));
        resultMap.put("waitOrder", waitOrder);
        messageContext.setMapData(resultMap);
        messageContext.success();
    }
}
