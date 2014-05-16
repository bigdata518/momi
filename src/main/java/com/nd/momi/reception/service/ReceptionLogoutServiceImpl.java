package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
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

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.RECEPTION_LOGOUT,
        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "receptionName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        sessionHandleTypeEnum = SessionHandleTypeEnum.REMOVE,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "客服登出")
public class ReceptionLogoutServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String receptionId = SessionUtils.getReceptionIdFromSessionId(session.getSid());
        ReceptionEntity userEntity = this.receptionLocalService.inquireRecepitonById(receptionId);
        if (userEntity != null) {
            messageContext.setEntityData(userEntity);
            messageContext.success();
            //记录登出状态
            this.receptionLocalService.offService(userEntity.getReceptionId(), "登出");
        }
    }
}
