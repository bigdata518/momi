/**
 *
 */
package com.nd.momi.dialogue.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.dialogue.localservice.DialogueLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cy
 *
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_FINISH_DIALOGUE,
        requestConfigs = {
                @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
                @RequestConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id")
        },
        responseConfigs = {
                @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
                @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
        },
        validateSession = true,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "客户接通客服后结束对话，结束对话类型有4种")

public class CustomerFinishDialogueServiceImpl implements Service {

    @InjectLocalService()
    private DialogueLocalService dialogueLocalService;

    @Override
    public void execute(MessageContext messageContext) {

        Map<String, String> updateStateMap = new HashMap<String, String>(3, 1);
        updateStateMap.put("dialogueId", "");
        this.dialogueLocalService.updateDailogue(updateStateMap);
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerIdFromSessionId(sid);
        messageContext.setNewSession(null);
        String receptionId = messageContext.getParameter("receptionId");
        // 设置返回值
        Map<String, String> resultMap = new HashMap<String, String>(2, 1);
        resultMap.put("customerId", customerId);
        resultMap.put("receptionId", receptionId);
        messageContext.setMapData(resultMap);

        String serviceSid = SessionUtils.createReceptionSessionId(receptionId);
        messageContext.success();
        // 消息推送
        String responseMessage = messageContext.getResponseMessage();
        messageContext.push(serviceSid, responseMessage);
    }
}

