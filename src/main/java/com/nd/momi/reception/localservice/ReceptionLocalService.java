package com.nd.momi.reception.localservice;

import com.nd.momi.reception.entity.ReceptionEntity;
import com.nd.momi.reception.entity.ReceptionStateEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface ReceptionLocalService extends Local {

    public String adminUserName = "admin";
    public String adminUserId = "10000";

    public void insertReception(Map<String, String> entityMap);

    public ReceptionEntity inquireRecepitonById(String receptionId);
    
    public void deleteReception(String receptionId);

    public List<ReceptionEntity> inquireReception(long pageIndex, long pageSize);

    public List<ReceptionEntity> inquireReceptionDESC(long pageIndex, long pageSize);

    public long countReception();

    public void onService(ReceptionEntity entity);

    public void offService(String receptionId, String offMessage);

    public void comebackService(String receptionId);
    
    public List<ReceptionStateEntity> inquireOnService(long pageIndex, long pageSize);
}
