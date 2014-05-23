package com.nd.momi.customer.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.localservice.CustomerLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_LOGIN,
        requestConfigs = {
    @RequestConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id")
},
        responseConfigs = {
    @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @ResponseConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "昵称")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "客户用户登录")
public class CustomerLoginServiceImpl implements Service {

    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String customerId = messageContext.getParameter("customerId");
        CustomerEntity entity = this.customerLocalService.inquireCustomerById(customerId);
        if (entity != null) {
            //判断是否为空，session验证
            String sid = SessionUtils.createCustomerSessionId(customerId);
            Session session = new SessionImpl(sid);
            messageContext.setNewSession(session);
            messageContext.setEntityData(entity);
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
