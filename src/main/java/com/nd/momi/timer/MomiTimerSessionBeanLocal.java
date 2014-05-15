package com.nd.momi.timer;

import javax.ejb.Local;

/**
 *
 * @author jianying9
 */
@Local
public interface MomiTimerSessionBeanLocal {
    
    public void allotWaitCustomer();
}
