package com.nd.momi.leave;

import com.nd.momi.customer.localservice.CustomerLocalService;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.comet.CometContext;
import com.wolf.framework.comet.LeaveEventConfig;
import com.wolf.framework.comet.LeaveEventHandler;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.session.Session;

/**
 *
 * @author jianying9
 */
@LeaveEventConfig()
public class LogoutLeaveEventHandlerImpl implements LeaveEventHandler {

    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;
    //
    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(Session session, CometContext cometContext) {
        String sid = session.getSid();
        if(SessionUtils.isReceptionSession(sid)) {
            //客服登出
            String receptionId = SessionUtils.getReceptionIdFromSessionId(sid);
            this.receptionLocalService.offService(receptionId, "登出");
        } else {
            //客户登出
            String customerId = SessionUtils.getCustomerIdFromSessionId(sid);
            this.customerLocalService.deleteCustomerWait(customerId);
        }
//        cometContext.push("271411", "{\"flag\":\"SUCCESS\",\"act\":\"CUSTOMER_LOGOUT\",\"data\":{\"userId\":\"" + session.getSid() + "\",\"serviceId\":\"271411\"}}");
    }
}
