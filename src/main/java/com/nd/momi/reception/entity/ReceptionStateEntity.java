package com.nd.momi.reception.entity;

import com.nd.momi.config.TableNames;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.RColumnConfig;
import com.wolf.framework.dao.annotation.RDaoConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author aladdin
 */
@RDaoConfig(
        tableName = TableNames.RECEPTION_STATE,
        dbIndex = TableNames.RECEPTION_STATE_INDEX)
public final class ReceptionStateEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客服组用户id")
    private String receptionId;
    //
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "状态:on,off")
    private String state;
    //
    @RColumnConfig(desc = "off状态原因")
    private String offMessage;
    //
    @RColumnConfig(desc = "名称")
    private String receptionName;

    public String getReceptionId() {
        return receptionId;
    }

    public String getState() {
        return state;
    }

    public String getReceptionName() {
        return receptionName;
    }
    
    @Override
    public String getKeyValue() {
        return this.receptionId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("receptionId", this.receptionId);
        map.put("state", this.state);
        map.put("receptionName", this.receptionName);
        map.put("offMessage", this.offMessage);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.receptionId = entityMap.get("receptionId");
        this.state = entityMap.get("state");
        this.receptionName = entityMap.get("receptionName");
        this.offMessage = entityMap.get("offMessage");
    }
}
