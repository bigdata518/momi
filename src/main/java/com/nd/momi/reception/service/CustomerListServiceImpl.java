/**
 *
 */
package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.localservice.CustomerLocalService;
import com.nd.momi.dialogue.entity.DialogueEntity;
import com.nd.momi.dialogue.localservice.DialogueLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lgf
 *
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_LIST,
        responseConfigs = {
                @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
                @ResponseConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "客服名称")
        },
        validateSession = true,
        response = true,
        page = true,
        group = ActionGroupNames.RECEPTION,
        description = "客户接通客服后结束对话，结束对话类型有4种")

public class CustomerListServiceImpl implements Service {

    @InjectLocalService()
    private DialogueLocalService dialogueLocalService;

    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String sid = messageContext.getSession().getSid();
        String receptionId = SessionUtils.getReceptionIdFromSessionId(sid);
        List<DialogueEntity> dialogueEntityList = this.dialogueLocalService.inquireDialoguesByReceptionId(receptionId);
        List<CustomerEntity> customerEntityList = new ArrayList<CustomerEntity>();
        for(int i=0;i<dialogueEntityList.size();i++){
            String customerId = dialogueEntityList.get(i).getCustomerId();
            CustomerEntity customerEntity = this.customerLocalService.inquireCustomerById(customerId);
            customerEntityList.add(customerEntity);
        }
        messageContext.setPageTotal(dialogueEntityList.size());
        messageContext.setEntityListData(customerEntityList);
        messageContext.success();
    }
}

