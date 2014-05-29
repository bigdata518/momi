/**
 * 客户评价客服接口
 */
package com.nd.momi.customer.service;

import com.nd.momi.config.ActionGroupNames;
import com.nd.momi.config.ActionNames;
import com.nd.momi.config.FrontEndConfig;
import com.nd.momi.config.ResponseFlags;
import com.nd.momi.customer.localservice.ReceptionEvaluateLocalService;
import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.worker.context.MessageContext;

import java.util.Map;

/**
 * @author cy
 *
 */
@ServiceConfig(actionName = ActionNames.CUSTOMER_EVALUATE_RECEPTION, requestConfigs = {
        @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
        @RequestConfig(name = "score", typeEnum = TypeEnum.INT, desc = "星星得分"),
        @RequestConfig(name = "receptionQuality", typeEnum = TypeEnum.CHAR_32, must = false, desc = "客服服务"),
        @RequestConfig(name = "problemSolve", typeEnum = TypeEnum.CHAR_32, must = false, desc = "问题处理"),
        @RequestConfig(name = "suggestion", typeEnum = TypeEnum.CHAR_255, must = false, desc = "意见反馈") },

        validateSession = true, response = false, group = ActionGroupNames.CUSTOMER, description = "玩家结束对话后，对刚才的客服做评价")
public class CustomerEvaluateReceptionServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionEvaluateLocalService receptionEvaluateLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        // 获取输入参数 RequestConfig
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerIdFromSessionId(sid);

        String receptionId = parameterMap.get("receptionId");
        String suggestion = parameterMap.get("suggestion");
        int score = Integer.parseInt(parameterMap.get("score"));
        String receptionQuality = parameterMap.get("receptionQuality");
        String problemSolve = parameterMap.get("problemSolve");
        if (score == 1 || score == 2 || score == 3) {
            if (score == 1) {
                if (receptionQuality == null || problemSolve == null) {
                    messageContext.setFlag(ResponseFlags.FAILURE_ERROR_DATA);
                    return;
                }
            }
            this.receptionEvaluateLocalService.insertReceptionEvaluate(
                    customerId, receptionId, score, receptionQuality,
                    problemSolve, suggestion);
            messageContext.success();
            //
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ERROR_DATA);
        }
    }
}
