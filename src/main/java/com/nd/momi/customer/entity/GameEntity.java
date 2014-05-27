package com.nd.momi.customer.entity;

import com.nd.momi.config.TableNames;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.RColumnConfig;
import com.wolf.framework.dao.annotation.RDaoConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cy
 * 游戏选择表
 */
@RDaoConfig(
        tableName = TableNames.GAME,
        dbIndex = TableNames.GAME_INDEX)
public final class GameEntity extends Entity{
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY,desc = "游戏id")
    private String gameId;
    //
    @RColumnConfig(desc = "游戏名称")
    private String gameName;
    //
    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public String getKeyValue() {
        return this.gameId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String,String> map = new HashMap<String, String>(2,1);
        map.put("gameId",this.gameId);
        map.put("gameName",this.gameName);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.gameId = entityMap.get("gameId");
        this.gameName = entityMap.get("gameName");
    }
}
