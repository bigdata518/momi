
package com.nd.momi.customer.localservice;

import com.nd.momi.customer.entity.ReceptionEvaluateEntity;
import com.wolf.framework.local.Local;

import java.util.List;
import java.util.Map;

public interface ReceptionEvaluateLocalService extends Local{

    public void insertReceptionEvaluate(String customerId, String receptionId, int score, String receptionQuality, String problemSolve, String suggestion);

    public void deleteReceptionEvaluate(String evaluateId);

    public List<ReceptionEvaluateEntity> inquireReceptionEvaluate();

    public List<ReceptionEvaluateEntity> inquireReceptionEvaluate(long pageIndex, long pageSize);
}