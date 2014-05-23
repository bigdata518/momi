package com.nd.momi.bulletin.service;

import com.nd.momi.bulletin.localservice.BulletinLocalService;
import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
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
 * @author lgf
 */
@ServiceConfig(
        actionName = ActionNames.DELETE_BULLETIN,
        requestConfigs = {
    @RequestConfig(name = "bulletinType", typeEnum = TypeEnum.CHAR_32, desc = "公告类型"),
        },
        responseConfigs = {
    @ResponseConfig(name = "bulletinType", typeEnum = TypeEnum.CHAR_32, desc = "公告类型"),
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.BULLETIN,
        description = "删除公告")
public class DeleteBulletinServiceImpl implements Service {

    @InjectLocalService()
    private BulletinLocalService bulletinLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String bulletinType = parameterMap.get("bulletinType");
        this.bulletinLocalService.deleteBulletin(bulletinType);
        messageContext.setMapData(parameterMap);
        messageContext.success();
    }
}
