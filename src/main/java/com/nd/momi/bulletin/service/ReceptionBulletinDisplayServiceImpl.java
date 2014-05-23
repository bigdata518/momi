package com.nd.momi.bulletin.service;

import com.nd.momi.bulletin.entity.BulletinEntity;
import com.nd.momi.bulletin.localservice.BulletinLocalService;
import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.localservice.CustomerLocalService;
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
        actionName = ActionNames.RECEPTION_BULLETIN_DISPLAY,
        responseConfigs = {
    @ResponseConfig(name = "title", typeEnum = TypeEnum.CHAR_32, desc = "标题"),
    @ResponseConfig(name = "content", typeEnum = TypeEnum.CHAR_32, desc = "公告内容")
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
        BulletinEntity entity = this.bulletinLocalService.inquireBulletinByType("RECEPTION");
        messageContext.setEntityData(entity);
        messageContext.success();

    }
}
