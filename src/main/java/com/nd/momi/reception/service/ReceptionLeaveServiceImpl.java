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
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;

import java.util.Map;

/**
 *
 * @author lgf
 */
@ServiceConfig(
        actionName = ActionNames.RECEPTION_LEAVE,
        requestConfigs = {
    @RequestConfig(name = "offMessage", typeEnum = TypeEnum.CHAR_32, desc = "离开理由")
        },

        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "offMessage", typeEnum = TypeEnum.CHAR_32, desc = "离开理由")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "客服离开")
public class ReceptionLeaveServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        Session session = messageContext.getSession();
        String receptionId = SessionUtils.getReceptionIdFromSessionId(session.getSid());
        ReceptionEntity userEntity = this.receptionLocalService.inquireRecepitonById(receptionId);
        if (userEntity != null) {
            parameterMap.put("receptionId",receptionId);
            String offMessage = messageContext.getParameter("offMessage");
            this.receptionLocalService.offService(receptionId,offMessage);
            messageContext.setMapData(parameterMap);
            messageContext.success();
        }else{
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
