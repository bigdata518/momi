package com.nd.momi.bulletin.localservice;

import com.nd.momi.bulletin.entity.BulletinEntity;
import com.nd.momi.bulletin.entity.BulletinType;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.LocalServiceConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lgf on 2014/5/21.
 */
@LocalServiceConfig(
        interfaceInfo =BulletinLocalService.class,
        description = "客户管理")
public class BulletinLocalServiceImpl implements BulletinLocalService{

    @InjectRDao(clazz = BulletinEntity.class)
    private REntityDao<BulletinEntity> bulletinEntityDao;

    @Override
    public void insertBulletin(Map<String, String> entityMap) {
        this.bulletinEntityDao.insert(entityMap);
    }

    @Override
    public void updateBulletin(Map<String, String> entityMap){
        this.bulletinEntityDao.update(entityMap);
    }

    @Override
    public BulletinEntity inquireBulletinByType(String bulletinType) {
        return this.bulletinEntityDao.inquireByKey(bulletinType);
    }

    @Override
    public void deleteBulletin(String bulletinType) {
        this.bulletinEntityDao.delete(bulletinType);
    }

    @Override
    public List<BulletinEntity> inquireBulletin() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(2);
        inquirePageContext.setPageIndex(1);
        return this.bulletinEntityDao.inquire(inquirePageContext);
    }

    @Override
    public void init() {
        BulletinEntity customerBulletinEntity = this.bulletinEntityDao.inquireByKey("CUSTOMER");
        BulletinEntity receptionBulletinEntity = this.bulletinEntityDao.inquireByKey("RECEPTION");
        if(customerBulletinEntity == null){
            Map<String, String> entityMap = new HashMap<String, String>(4, 1);
            final long createTime = System.currentTimeMillis();
            entityMap.put("bulletinType", BulletinType.CUSTOMER);
            entityMap.put("title", "");
            entityMap.put("content", "");
            entityMap.put("createTime",Long.toString(createTime));
            this.bulletinEntityDao.insert(entityMap);
        }
        if(receptionBulletinEntity == null){
            Map<String, String> entityMap = new HashMap<String, String>(4, 1);
            final long createTime = System.currentTimeMillis();
            entityMap.put("bulletinType", BulletinType.RECEPTION);
            entityMap.put("title", "");
            entityMap.put("content", "");
            entityMap.put("createTime",Long.toString(createTime));
            this.bulletinEntityDao.insert(entityMap);
        }
    }
}
