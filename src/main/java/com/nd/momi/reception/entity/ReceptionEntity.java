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
        tableName = TableNames.RECEPTION,
        dbIndex = TableNames.RECEPTION_INDEX)
public final class ReceptionEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客服组用户id")
    private String receptionId;
    //
    @RColumnConfig(desc = "客服名称")
    private String receptionName;
    //
    @RColumnConfig(desc = "密码")
    private String password;
    //
    @RColumnConfig(desc = "客服类型:ADMIN-管理员,LEADER-组长,MEMBER-一般客服人员")
    private String type;

    public String getReceptionId() {
        return receptionId;
    }

    public String getReceptionName() {
        return receptionName;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getKeyValue() {
        return this.receptionId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("receptionId", this.receptionId);
        map.put("receptionName", this.receptionName);
        map.put("password", this.password);
        map.put("type", this.type);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.receptionId = entityMap.get("receptionId");
        this.receptionName = entityMap.get("receptionName");
        this.password = entityMap.get("password");
        this.type = entityMap.get("type");
    }
}
