package com.nd.momi.customer.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.localservice.CustomerLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_CUSTOMER,
        responseConfigs = {
    @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @ResponseConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "名称")
},
        validateSession = false,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "随机获取客户帐号")
public class InquireCustomerServiceImpl implements Service {

    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        //随机获客户账号信息
        CustomerEntity entity = this.customerLocalService.inquireCustomer();
        messageContext.setEntityData(entity);
        messageContext.success();
    }
}
