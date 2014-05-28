package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.reception.entity.ReceptionEntity;
import com.nd.momi.reception.entity.ReceptionTypeEnum;
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
import com.wolf.framework.session.SessionImpl;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.RECEPTION_LOGIN,
        requestConfigs = {
    @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "receptionName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "客服登录")
public class ReceptionLoginServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String receptionId = messageContext.getParameter("receptionId");
        ReceptionEntity serviceEntity = this.receptionLocalService.inquireRecepitonById(receptionId);
        if (serviceEntity != null) {
            String sid = SessionUtils.createReceptionSessionId(receptionId);
            Session session = new SessionImpl(sid);
            messageContext.setNewSession(session);
            messageContext.setEntityData(serviceEntity);
            messageContext.success();
            //记录登录状态
            //管理员不需要进入服务状态
            if(serviceEntity.getType().equals(ReceptionTypeEnum.ADMIN.name()) == false){
                this.receptionLocalService.offService(serviceEntity.getReceptionId(),"首次登陆");
            }
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
