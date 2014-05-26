package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.reception.entity.ReceptionEntity;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_RECEPTION,
        responseConfigs = {
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @ResponseConfig(name = "receptionName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        page = true,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "查询客服帐号")
public class InquireReceptionServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        long pageIndex = messageContext.getPageIndex();
        long pageSize = messageContext.getPageSize();
        long pageTotal = this.receptionLocalService.countReception();
        messageContext.setPageTotal(pageTotal);
        List<ReceptionEntity> userEntityList = this.receptionLocalService.inquireReceptionDESC(pageIndex, pageSize);
        messageContext.setEntityListData(userEntityList);
        messageContext.success();
    }
}
