package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.DELETE_RECEPTION,
        requestConfigs = {
    @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "删除客服帐号")
public class DeleteReceptionServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String receptionId = parameterMap.get("receptionId");
        this.receptionLocalService.deleteReception(receptionId);
        messageContext.setMapData(parameterMap);
        messageContext.success();
    }
}
