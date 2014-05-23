package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.reception.entity.ReceptionEntity;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lgf
 */
@ServiceConfig(
        actionName = ActionNames.RECEPTION_COMEBACK,
        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "客服回岗")
public class ReceptionComebackServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = new HashMap<String, String>(2,1);
        Session session = messageContext.getSession();
        String receptionId = SessionUtils.getReceptionIdFromSessionId(session.getSid());
        ReceptionEntity userEntity = this.receptionLocalService.inquireRecepitonById(receptionId);
        if (userEntity != null) {
            parameterMap.put("receptionId",receptionId);
            this.receptionLocalService.comebackService(receptionId);
            messageContext.setMapData(parameterMap);
            messageContext.success();
        }else{
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
