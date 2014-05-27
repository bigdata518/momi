/**
 *
 */
package com.nd.momi.dialogue.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.dialogue.entity.DialogueEndState;
import com.nd.momi.dialogue.entity.DialogueEntity;
import com.nd.momi.dialogue.localservice.DialogueLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.Map;

/**
 * @author cy
 *
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_FINISH_DIALOGUE,
        requestConfigs = {
                @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
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

        Map<String, String> parameterMap = messageContext.getParameterMap();
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerIdFromSessionId(sid);
        String receptionId = parameterMap.get("receptionId");
        //根据customerId查询当前对话实体
        DialogueEntity dialogueEntity = this.dialogueLocalService.inquireDialogueByCustomerId(customerId);
        //将当前对话删除
        this.dialogueLocalService.deleteDialogue(customerId);
        Map<String,String> map = dialogueEntity.toMap();
        //将状态置为正常结束
        map.put("state", DialogueEndState.NORMAL_END);
        //将记录插入到历史对话表
        this.dialogueLocalService.insertDialogueHistory(map);
        parameterMap.put("customerId",customerId);
        messageContext.setMapData(parameterMap);
        String serviceSid = SessionUtils.createReceptionSessionId(receptionId);
        messageContext.success();
        // 消息推送
        String responseMessage = messageContext.getResponseMessage();
        messageContext.push(serviceSid, responseMessage);

    }
}

