package com.nd.momi.bulletin.service;

import com.nd.momi.bulletin.entity.BulletinEntity;
import com.nd.momi.bulletin.localservice.BulletinLocalService;
import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.List;

/**
 * @author lgf
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_BULLETIN,
        responseConfigs = {
                @ResponseConfig(name = "bulletinType", typeEnum = TypeEnum.CHAR_32, desc = "公告类型"),
                @ResponseConfig(name = "title", typeEnum = TypeEnum.CHAR_32, desc = "标题"),
                @ResponseConfig(name = "content", typeEnum = TypeEnum.CHAR_32, desc = "公告内容"),
                @ResponseConfig(name = "createTime", typeEnum = TypeEnum.DATE_TIME, desc = "创建时间")
        },
        validateSession = true,
        page = true,
        response = true,
        group = ActionGroupNames.BULLETIN,
        description = "查询公告")
public class InquireBulletinServiceImpl implements Service {

    @InjectLocalService()
    private BulletinLocalService bulletinLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        List<BulletinEntity> bulletinEntityList = this.bulletinLocalService.inquireBulletin();
        messageContext.setPageTotal(bulletinEntityList.size());
        messageContext.setEntityListData(bulletinEntityList);
        messageContext.success();
    }
}
