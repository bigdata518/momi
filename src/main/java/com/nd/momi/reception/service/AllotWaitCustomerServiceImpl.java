package com.nd.momi.reception.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.customer.entity.WaitCustomerEntity;
import com.nd.momi.customer.localservice.CustomerLocalService;
import com.nd.momi.dialogue.localservice.DialogueLocalService;
import com.nd.momi.reception.entity.ReceptionStateEntity;
import com.nd.momi.reception.localservice.ReceptionLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.task.InjectTaskExecutor;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import com.wolf.framework.worker.context.MessageContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@ServiceConfig(
        actionName = ActionNames.ALLOT_WAIT_CUSTOMER,
        responseConfigs = {
    @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @ResponseConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "客户昵称"),
    @ResponseConfig(name = "waitOrder", typeEnum = TypeEnum.CHAR_32, desc = "客户昵称"),
    @ResponseConfig(name = "gameId", typeEnum = TypeEnum.CHAR_32, desc = "游戏id"),
    @ResponseConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "receptionName", typeEnum = TypeEnum.CHAR_32, desc = "客服昵称"),
    @ResponseConfig(name = "state", typeEnum = TypeEnum.CHAR_32, desc = "调度服务状态:stop-停止,running-运行")
},
        validateSession = false,
        response = true,
        group = ActionGroupNames.RECEPTION,
        description = "分配等待队列中的客户")
public class AllotWaitCustomerServiceImpl implements Service {

    //状态：stop-正常停止,running-运行中
    private volatile String state = "stop";
    //
    @InjectLocalService()
    private ReceptionLocalService receptionLocalService;
    //
    @InjectLocalService()
    private CustomerLocalService customerLocalService;
    //
    @InjectLocalService()
    private DialogueLocalService dialogueLocalService;
    //
    @InjectTaskExecutor
    private TaskExecutor taskExecutor;

    @Override
    public void execute(MessageContext messageContext) {
        synchronized (this) {
            if (this.state.equals("stop")) {
                this.state = "running";
                Task task = new AllotTaskImpl(messageContext);
                this.taskExecutor.submit(task);
            }
        }
        Map<String, String> resultMap = new HashMap<String, String>(2, 1);
        resultMap.put("state", this.state);
        messageContext.setMapData(resultMap);
        messageContext.success();
    }

    /**
     * 执行任务
     */
    private class AllotTaskImpl extends Task {

        private final MessageContext messageContext;

        public AllotTaskImpl(MessageContext messageContext) {
            this.messageContext = messageContext;
        }

        @Override
        public void doWhenRejected() {
            state = "stop";
        }

        @Override
        protected void execute() {
            List<WaitCustomerEntity> waitCustomerEntityList;
            List<ReceptionStateEntity> serviceStateEntityList;
            while (state.equals("running")) {
                //获取在线的前50处于接收状态(on)的客服列表
                serviceStateEntityList = receptionLocalService.inquireOnService(1, 100);
                if (serviceStateEntityList.isEmpty() == false) {
                    //有在线客服
                    //获取5倍与接收状态的客服数量的等待客户数量客户
                    waitCustomerEntityList = customerLocalService.inquireCustomerWait(1, 500);
                    if (waitCustomerEntityList.isEmpty() == false) {
                        //有等待的客户
                        int serviceIndex = 0;
                        ReceptionStateEntity serviceStateEntity;
                        WaitCustomerEntity waitCustomerEntity;
                        Map<String, String> resultMap = new HashMap<String, String>(4, 1);
                        String customerSid;
                        String serviceSid;
                        String responseMessage;
                        //按排队顺序为等待中的客户分配客服
                        for (int waitIndex = 0; waitIndex < waitCustomerEntityList.size(); waitIndex++) {
                            //当全部客服都接收客户后进行新一轮的配对
                            if (serviceIndex >= serviceStateEntityList.size()) {
                                serviceIndex = serviceIndex % serviceStateEntityList.size();
                            }
                            //获取客户和客服
                            waitCustomerEntity = waitCustomerEntityList.get(waitIndex);
                            serviceStateEntity = serviceStateEntityList.get(0);
                            String receptionId = serviceStateEntity.getReceptionId();
                            String customerId = waitCustomerEntity.getCustomerId();
                            String gameId = waitCustomerEntity.getGameId();
                            //插入到对话对话表中
                            dialogueLocalService.insertDialogue(customerId,receptionId,gameId);
                            //返回数据
                            resultMap.put("receptionId", receptionId);
                            resultMap.put("receptionName", serviceStateEntity.getReceptionName());
                            resultMap.put("customerId", customerId);
                            resultMap.put("gameId",gameId);
                            resultMap.put("customerName", waitCustomerEntity.getCustomerName());
                            resultMap.put("waitOrder", Long.toString(waitCustomerEntity.getWaitOrder()));
                            this.messageContext.setMapData(resultMap);
                            responseMessage = this.messageContext.getResponseMessage(false);
                            //消息推送给配对的客服和客户
                            customerSid = SessionUtils.createCustomerSessionId(waitCustomerEntity.getCustomerId());
                            serviceSid = SessionUtils.createReceptionSessionId(serviceStateEntity.getReceptionId());
                            this.messageContext.push(customerSid, responseMessage);
                            this.messageContext.push(serviceSid, responseMessage);
                            //在等待队列中删除已配对的客户
                            customerLocalService.deleteCustomerWait(waitCustomerEntity.getCustomerId());
                            //通知排在当前配对的客户之后的客户，更新等待人数
                            for (int index = waitIndex + 1; index < waitCustomerEntityList.size(); index++) {
                                waitCustomerEntity = waitCustomerEntityList.get(index);
                                customerSid = SessionUtils.createCustomerSessionId(waitCustomerEntity.getCustomerId());
                                this.messageContext.push(customerSid, responseMessage);
                            }
                            serviceIndex++;
                        }
                    } else {
                        state = "stop";
                    }
                } else {
                    state = "stop";
                }
            }
        }
    }
}
