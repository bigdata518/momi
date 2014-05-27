package com.nd.momi.customer.localservice;

import com.nd.momi.customer.entity.CustomerEntity;
import com.nd.momi.customer.entity.WaitCustomerEntity;
import com.wolf.framework.local.Local;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface CustomerLocalService extends Local {

    public void insertCustomer(Map<String, String> entityMap);

    public CustomerEntity inquireCustomer();

    public CustomerEntity inquireCustomerById(String customerId);

    public long countWaitCustomerNum();

    public void insertWaitCustomer(String customerId, String customerName, String createTime, String gameId);
    
    public WaitCustomerEntity inquireWaitCustomerById(String customerId);

    public void deleteCustomerWait(String customerId);
    
    public List<WaitCustomerEntity> inquireCustomerWait(long pageIndex, long pageSize);
}
