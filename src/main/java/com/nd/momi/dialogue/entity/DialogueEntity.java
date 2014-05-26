/**
 *
 */
package com.nd.momi.dialogue.entity;

import com.nd.momi.config.TableNames;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.RColumnConfig;
import com.wolf.framework.dao.annotation.RDaoConfig;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lgf
 *
 */
@RDaoConfig(
        tableName = TableNames.DIALOGUE,
        dbIndex = TableNames.DIALOGUE_INDEX)

public final class DialogueEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客户id")
    private String customerId;
    //
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "客服id")
    private String receptionId;
    //
    @RColumnConfig(desc = "游戏Id")
    private String gameId;
    //
    @RColumnConfig(desc = "对话开始时间")
    private long createTime;

    public String getCustomerId() {
        return customerId;
    }

    public String getReceptionId() {
        return receptionId;
    }

    public String getGameId() {
        return gameId;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.customerId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("customerId", this.customerId);
        map.put("receptionId", this.receptionId);
        map.put("gameId", this.gameId);
        map.put("createTime", String.valueOf(this.createTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.customerId = entityMap.get("customerId");
        this.receptionId = entityMap.get("receptionId");
        this.gameId = entityMap.get("gameId");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
    }
}
