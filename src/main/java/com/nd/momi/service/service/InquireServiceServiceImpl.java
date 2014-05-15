package com.nd.momi.service.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.service.entity.ServiceEntity;
import com.nd.momi.service.localservice.ServiceLocalService;
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
        actionName = ActionNames.INQUIRE_SERVICE,
        responseConfigs = {
    @ResponseConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @ResponseConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        page = true,
        response = true,
        group = ActionGroupNames.SERVICE,
        description = "查询客服帐号")
public class InquireServiceServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        long pageIndex = messageContext.getPageIndex();
        long pageSize = messageContext.getPageSize();
        long pageTotal = this.serviceUserLocalService.countService();
        messageContext.setPageTotal(pageTotal);
        List<ServiceEntity> userEntityList = this.serviceUserLocalService.inquireServiceDESC(pageIndex, pageSize);
        messageContext.setEntityListData(userEntityList);
        messageContext.success();
    }
}
