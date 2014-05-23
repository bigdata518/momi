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
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.List;

/**
 * @author cy
 *
 */
@ServiceConfig(
        actionName = ActionNames.GAME_LIST,
        responseConfigs = {
                @ResponseConfig(name = "gameId", typeEnum = TypeEnum.CHAR_32, desc = "游戏id"),
                @ResponseConfig(name = "gameName", typeEnum = TypeEnum.CHAR_32, desc = "游戏名称")
        },
        validateSession = true,
        page = true,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "玩家登陆后，显示游戏列表供玩家选择")

public class GameListServiceImpl implements Service {

    @InjectLocalService()
    private GameLocalService gameLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        List<GameEntity> gameEntityList = this.gameLocalService.getGames();
        if (gameEntityList != null) {
            messageContext.setEntityListData(gameEntityList);
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_NO_GAME);
        }
    }
}
