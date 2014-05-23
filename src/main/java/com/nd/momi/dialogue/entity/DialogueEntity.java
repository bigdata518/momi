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
 * @author cy
 *
 */
@RDaoConfig(
        tableName = TableNames.DIALOGUE,
        dbIndex = TableNames.DIALOGUE_INDEX)

public final class DialogueEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "对话id")
    private String dialogueId;
    //
    @RColumnConfig(desc = "客户id")
    private String customerId;
    //
    @RColumnConfig(desc = "客服Id")
    private String receptionId;
    //
    @RColumnConfig(desc = "游戏Id")
    private String gameId;
    //
    @RColumnConfig(desc = "对话开始时间")
    private long createTime;
    //
    @RColumnConfig(desc = "对话结束时间")
    private long endTime;
    //
    @RColumnConfig(desc = "状态，开始对话或结束对话，结束对话有5种状态")
    private String state;

    public String getDialogueId() {
        return dialogueId;
    }

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

    public long getEndTime() {
        return endTime;
    }

    public String getState() {
        return state;
    }

    @Override
    public String getKeyValue() {
        return this.dialogueId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(7, 1);
        map.put("dialogueId", this.dialogueId);
        map.put("customerId", this.customerId);
        map.put("receptionId", this.receptionId);
        map.put("gameId", this.gameId);
        map.put("createTime", String.valueOf(this.createTime));
        map.put("endTime", String.valueOf(this.endTime));
        map.put("state", this.state);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.dialogueId = entityMap.get("dialogueId");
        this.customerId = entityMap.get("customerId");
        this.receptionId = entityMap.get("receptionId");
        this.gameId = entityMap.get("gameId");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
        this.endTime = Long.parseLong(entityMap.get("endTime"));
        this.state = entityMap.get("state");
    }
}
