package com.nd.momi.message.localservice;

import com.nd.momi.message.entity.MessageEntity;
import com.wolf.framework.local.Local;
import java.util.List;

/**
 *
 * @author jianying9
 */
public interface MessageLocalService extends Local {

    public MessageEntity insertTextMessageFormCustomer(String receptionId, String customerId, String message);

    public MessageEntity insertTextMessageFormReception(String receptionId, String customerId, String message);

    public List<MessageEntity> inquireMessageByCustomerDESC(String customerId, long pageIndex, long pageSize);
}
