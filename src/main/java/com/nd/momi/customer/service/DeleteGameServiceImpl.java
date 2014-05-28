/**
 *
 */
package com.nd.momi.customer.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.customer.entity.GameEntity;
import com.nd.momi.customer.localservice.GameLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author cy
 *
 */
@ServiceConfig(
        actionName = ActionNames.DELETE_GAME,
        requestConfigs = {
                @RequestConfig(name = "gameId", typeEnum = TypeEnum.CHAR_32, desc = "游戏id"),
        },
        responseConfigs = {
                @ResponseConfig(name = "gameId", typeEnum = TypeEnum.CHAR_32, desc = "游戏id"),
        },
        validateSession = true,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "删除游戏")

public class DeleteGameServiceImpl implements Service {

    @InjectLocalService()
    private GameLocalService gameLocalService;

    @Override
    public void execute(MessageContext messageContext) {

        Map<String, String> parameterMap = messageContext.getParameterMap();
        String gameId = parameterMap.get("gameId");
        this.gameLocalService.deleteGame(gameId);
        messageContext.setMapData(parameterMap);
        messageContext.success();
    }
}

