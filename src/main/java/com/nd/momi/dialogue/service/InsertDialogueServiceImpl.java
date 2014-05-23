/**
 *
 */
package com.nd.momi.dialogue.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
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
 * @author lgf
 *
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_DIALOGUE,
        requestConfigs = {
                @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
                @RequestConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
                @RequestConfig(name = "gameId", typeEnum = TypeEnum.CHAR_32, desc = "游戏id")
        },
        validateSession = true,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "客服与玩家配对后插入对话")

public class InsertDialogueServiceImpl implements Service {

    @InjectLocalService()
    private DialogueLocalService dialogueLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String customerId = parameterMap.get("customerId");
        String receptionId = parameterMap.get("receptionId");
        String gameId = parameterMap.get("gameId");
        this.dialogueLocalService.insertDialogue(customerId,receptionId,gameId);
        messageContext.success();
    }
}

