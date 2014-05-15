package com.nd.momi.service.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.service.entity.ServiceEntity;
import com.nd.momi.service.localservice.ServiceLocalService;
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
        actionName = ActionNames.SERVICE_LOGIN,
        requestConfigs = {
    @RequestConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        responseConfigs = {
    @ResponseConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.SERVICE,
        description = "客服登录")
public class ServiceLoginServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String serviceId = messageContext.getParameter("serviceId");
        ServiceEntity serviceEntity = this.serviceUserLocalService.inquireServiceById(serviceId);
        if (serviceEntity != null) {
            String sid = SessionUtils.createServiceSessionId(serviceId);
            Session session = new SessionImpl(sid);
            messageContext.setNewSession(session);
            messageContext.setEntityData(serviceEntity);
            messageContext.success();
            //记录登录状态
            this.serviceUserLocalService.onService(serviceEntity);
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
