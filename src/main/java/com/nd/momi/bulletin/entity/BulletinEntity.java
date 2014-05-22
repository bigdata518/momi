package com.nd.momi.bulletin.entity;

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
        tableName = TableNames.BULLETIN,
        dbIndex = TableNames.BULLETIN_INDEX)
public final class BulletinEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "类型")
    private String bulletinType;
    //
    @RColumnConfig(desc = "标题")
    private String title;

    @RColumnConfig(desc = "公告内容")
    private String content;
    //
    @RColumnConfig(desc = "创建时间")
    private long createTime;

    public String getBulletinType() {
        return bulletinType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.bulletinType;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("bulletinType", this.bulletinType);
        map.put("title", this.title);
        map.put("content", this.content);
        map.put("createTime", Long.toString(this.createTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.bulletinType = entityMap.get("bulletinType");
        this.title = entityMap.get("title");
        this.content = entityMap.get("content");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
    }
}
