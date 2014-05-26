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
 * 服务评价表
 */
@RDaoConfig(
        tableName = TableNames.RECEPTION_EVALUATE,
        dbIndex = TableNames.RECEPTION_EVALUATE_INDEX)


public final class ReceptionEvaluateEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "评价id")
    private String evaluateId;
    //
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "客服id")
    private String receptionId;
    //
    @RColumnConfig(desc = "客户id")
    private String customerId;
    //
    @RColumnConfig(desc = "得分")
    private int score;
    //
    @RColumnConfig(desc = "客服服务")
    private String receptionQuality;
    //
    @RColumnConfig(desc = "问题处理")
    private String problemSolve;
    //
    @RColumnConfig(desc = "意见反馈")
    private String suggestion;



    public String getEvaluateId() {
        return evaluateId;
    }

    public String getReceptionId() {
        return receptionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getScore() {
        return score;
    }

    public String getReceptionQuality() {
        return receptionQuality;
    }

    public String getProblemSolve() {
        return problemSolve;
    }

    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public String getKeyValue() {
        return this.evaluateId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(7, 1);
        map.put("evaluateId", this.evaluateId);
        map.put("receptionId", this.receptionId);
        map.put("customerId", this.customerId);
        map.put("score", String.valueOf(this.score));
        map.put("receptionQuality", this.receptionQuality);
        map.put("problemSolve", this.problemSolve);
        map.put("suggestion", this.suggestion);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.evaluateId = entityMap.get("evaluateId");
        this.receptionId = entityMap.get("receptionId");
        this.customerId = entityMap.get("customerId");
        this.score = Integer.parseInt(entityMap.get("score"));
        this.receptionQuality = entityMap.get("receptionQuality");
        this.problemSolve = entityMap.get("problemSolve");
        this.suggestion = entityMap.get("suggestion");
    }
}