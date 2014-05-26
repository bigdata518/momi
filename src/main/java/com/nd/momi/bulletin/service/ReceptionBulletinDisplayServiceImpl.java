package com.nd.momi.bulletin.service;

import com.nd.momi.bulletin.entity.BulletinEntity;
import com.nd.momi.bulletin.entity.BulletinType;
import com.nd.momi.bulletin.localservice.BulletinLocalService;
import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author lgf
 */
@ServiceConfig(
        actionName = ActionNames.RECEPTION_BULLETIN_DISPLAY,
        responseConfigs = {
    @ResponseConfig(name = "title", typeEnum = TypeEnum.CHAR_32, desc = "标题"),
    @ResponseConfig(name = "content", typeEnum = TypeEnum.CHAR_4000, desc = "公告内容")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.BULLETIN,
        description = "客服公告显示")
public class ReceptionBulletinDisplayServiceImpl implements Service {

    @InjectLocalService()
    private BulletinLocalService bulletinLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        BulletinEntity entity = this.bulletinLocalService.inquireBulletinByType(BulletinType.RECEPTION);
        messageContext.setEntityData(entity);
        messageContext.success();

    }
}
