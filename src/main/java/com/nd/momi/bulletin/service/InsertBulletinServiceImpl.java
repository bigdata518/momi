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
 * @author lgf
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_BULLETIN,
        requestConfigs = {
                @RequestConfig(name = "bulletinType", typeEnum = TypeEnum.CHAR_32, desc = "公告类型"),
                @RequestConfig(name = "title", typeEnum = TypeEnum.CHAR_32, desc = "标题"),
                @RequestConfig(name = "content", typeEnum = TypeEnum.CHAR_4000, desc = "公告内容"),
        },
        responseConfigs = {
                @ResponseConfig(name = "bulletinType", typeEnum = TypeEnum.CHAR_32, desc = "公告类型"),
                @ResponseConfig(name = "title", typeEnum = TypeEnum.CHAR_32, desc = "标题"),
                @ResponseConfig(name = "content", typeEnum = TypeEnum.CHAR_4000, desc = "公告内容"),
                @ResponseConfig(name = "createTime", typeEnum = TypeEnum.DATE_TIME, desc = "创建时间")
        },
        validateSession = true,
        response = true,
        group = ActionGroupNames.BULLETIN,
        description = "添加公告")
public class InsertBulletinServiceImpl implements Service {

    @InjectLocalService()
    private BulletinLocalService bulletinLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        final long createTime = System.currentTimeMillis();
        parameterMap.put("createTime", Long.toString(createTime));
        this.bulletinLocalService.insertBulletin(parameterMap);
        messageContext.setMapData(parameterMap);
        messageContext.success();
    }
}
