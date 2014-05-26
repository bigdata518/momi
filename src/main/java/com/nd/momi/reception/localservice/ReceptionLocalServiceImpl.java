package com.nd.momi.reception.localservice;

import com.nd.momi.reception.entity.ReceptionEntity;
import com.nd.momi.reception.entity.ReceptionStateEntity;
import com.nd.momi.reception.entity.ReceptionTypeEnum;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquireIndexPageContext;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.LocalServiceConfig;
import com.wolf.framework.utils.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@LocalServiceConfig(
        interfaceInfo = ReceptionLocalService.class,
        description = "客服用户管理")
public class ReceptionLocalServiceImpl implements ReceptionLocalService {

    @InjectRDao(clazz = ReceptionEntity.class)
    private REntityDao<ReceptionEntity> receptionEntityDao;
    //
    @InjectRDao(clazz = ReceptionStateEntity.class)
    private REntityDao<ReceptionStateEntity> receptionStateEntityDao;

    @Override
    public void init() {
        //检测管理员用户是否存在
        ReceptionEntity adminUserEntity = this.receptionEntityDao.inquireByKey(ReceptionLocalService.adminUserId);
        if (adminUserEntity == null) {
            //管理员用户不存在，初始化
            Map<String, String> entityMap = new HashMap<String, String>(4, 1);
            entityMap.put("receptionId", ReceptionLocalService.adminUserId);
            entityMap.put("receptionName", ReceptionLocalService.adminUserName);
            entityMap.put("type", ReceptionTypeEnum.ADMIN.name());
            entityMap.put("password", SecurityUtils.encryptByMd5(ReceptionLocalService.adminUserName));
            this.receptionEntityDao.insert(entityMap);
        }
    }

    @Override
    public void insertReception(Map<String, String> entityMap) {
        this.receptionEntityDao.insert(entityMap);
    }

    @Override
    public ReceptionEntity inquireRecepitonById(String receptionId) {
        return this.receptionEntityDao.inquireByKey(receptionId);
    }

    @Override
    public void deleteReception(String serviceId) {
        this.receptionEntityDao.delete(serviceId);
    }

    @Override
    public List<ReceptionEntity> inquireReception(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.receptionEntityDao.inquire(inquirePageContext);
    }

    @Override
    public long countReception() {
        return this.receptionEntityDao.count();
    }

    @Override
    public List<ReceptionEntity> inquireReceptionDESC(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.receptionEntityDao.inquireDESC(inquirePageContext);
    }

    @Override
    public void onService(ReceptionEntity entity) {
        Map<String, String> entityMap = entity.toMap();
        entityMap.put("state", "on");
        this.receptionStateEntityDao.insert(entityMap);
    }

    @Override
    public void offService(String receptionId, String offMessage) {
        Map<String, String> entityMap = new HashMap<String, String>(4, 1);
        entityMap.put("receptionId", receptionId);
        entityMap.put("offMessage", offMessage);
        entityMap.put("state", "off");
        this.receptionStateEntityDao.update(entityMap);
    }

    @Override
    public void comebackService(String receptionId) {
        Map<String, String> entityMap = new HashMap<String, String>(4, 1);
        entityMap.put("receptionId", receptionId);
        entityMap.put("state", "on");
        this.receptionStateEntityDao.update(entityMap);
    }

    @Override
    public List<ReceptionStateEntity> inquireOnService(long pageIndex, long pageSize) {
        InquireIndexPageContext inquirePageContext = new InquireIndexPageContext("state", "on");
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.receptionStateEntityDao.inquireByIndex(inquirePageContext);
    }
}
