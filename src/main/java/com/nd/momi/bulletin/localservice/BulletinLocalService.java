package com.nd.momi.bulletin.localservice;

import com.nd.momi.bulletin.entity.BulletinEntity;
import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.entity.WaitCustomerEntity;
import com.wolf.framework.local.Local;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface BulletinLocalService extends Local {

    public void insertBulletin(Map<String, String> entityMap);

    public void updateBulletin(Map<String, String> entityMap);

    public BulletinEntity inquireBulletinByType(String bulletinType);

    public void deleteBulletin(String bulletinType);
    
    public List<BulletinEntity> inquireBulletin();
}
