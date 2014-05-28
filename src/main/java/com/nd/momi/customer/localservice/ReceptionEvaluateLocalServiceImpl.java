/**
 *
 */
package com.nd.momi.customer.localservice;

import com.nd.momi.config.TableNames;
import com.nd.momi.customer.entity.ReceptionEvaluateEntity;
import com.nd.momi.key.localservice.KeyLocalService;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 *
 */


@LocalServiceConfig(interfaceInfo = ReceptionEvaluateLocalService.class, description = "服务评价管理")
public class ReceptionEvaluateLocalServiceImpl implements
        ReceptionEvaluateLocalService {

    @InjectLocalService()
    private KeyLocalService keyLocalService;

    @InjectRDao(clazz = ReceptionEvaluateEntity.class)
    private REntityDao<ReceptionEvaluateEntity> receptionEvaluateEntityDao;

    private long nextEvaluateId() {
        return this.keyLocalService.nextKeyValue(TableNames.RECEPTION_EVALUATE);
    }


    /* (non-Javadoc)
     * @see com.wolf.framework.local.Local#init()
     */
    @Override
    public void init() {
        long maxKeyValue = this.keyLocalService.getMaxKeyValue(TableNames.RECEPTION_EVALUATE);
        if (maxKeyValue < 100000) {
            this.keyLocalService.updateMaxKeyValue(TableNames.RECEPTION_EVALUATE, 100000);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.nd.momi.customer.localservice.ReceptionEvaluateLocalService#
     * insertReceptionEvaluate(java.lang.String, java.lang.String, int,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void insertReceptionEvaluate(final String customerId,
                                        final String receptionId, final int score,
                                        final String receptionQuality, final String problemSolve,
                                        final String suggestion) {
        final long evaluateId = this.nextEvaluateId();
        Map<String, String> entityMap = new HashMap<String, String>(8, 1);
        entityMap.put("evaluateId", String.valueOf(evaluateId));
        entityMap.put("customerId", customerId);
        entityMap.put("receptionId", receptionId);
        entityMap.put("score", String.valueOf(score));
        entityMap.put("receptionQuality", receptionQuality);
        entityMap.put("problemSolve", problemSolve);
        entityMap.put("suggestion", suggestion);
        this.receptionEvaluateEntityDao.insert(entityMap);
    }

    /* 插入并获取一个评价
     * @param customerId the String
     * 客户Id.
     * @param receptionId the String
     * 客服Id.
     * @param score int
     * 星星评分.
     * @param receptionQuality the String
     * 服务质量.
     * @param problemSolve the String
     * 问题解决.
     * @param suggestion the String
     * 意见反馈.
     *
     */

    public void deleteReceptionEvaluate(String evaluateId) {
        this.receptionEvaluateEntityDao.delete(evaluateId);
    }

    /*
     * @see com.nd.momi.customer.localservice.ReceptionEvaluateLocalService#
     * inquireReceptionEvaluate()
     */
    @Override
    public List<ReceptionEvaluateEntity> inquireReceptionEvaluate() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        return this.receptionEvaluateEntityDao.inquire(inquirePageContext);
    }

    /*
     * @see com.nd.momi.customer.localservice.ReceptionEvaluateLocalService#
     * inquireReceptionEvaluate(long, long)
     */
    @Override
    public List<ReceptionEvaluateEntity> inquireReceptionEvaluate(
            long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.receptionEvaluateEntityDao.inquire(inquirePageContext);
    }

}