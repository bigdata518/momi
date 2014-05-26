package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.reception.entity.ReceptionEntity;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.utils.SecurityUtils;
import com.wolf.framework.worker.context.MessageContext;

import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_RECEPTION,
        requestConfigs = {
    @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @RequestConfig(name = "receptionName", typeEnum = TypeEnum.CHAR_32, desc = "客服名称"),
    @RequestConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "receptionName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "新增客服帐号")
public class InsertReceptionServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String receptionId = parameterMap.get("receptionId");
        ReceptionEntity userEntity = this.receptionLocalService.inquireRecepitonById(receptionId);
        if(userEntity == null) {
            parameterMap.put("password", SecurityUtils.encryptByMd5(receptionId));
            this.receptionLocalService.insertReception(parameterMap);
            messageContext.setMapData(parameterMap);
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_USED);
        }
    }
}
