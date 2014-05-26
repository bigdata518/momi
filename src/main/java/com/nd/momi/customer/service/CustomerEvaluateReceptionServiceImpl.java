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
        @RequestConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
        @RequestConfig(name = "receptionId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
        @RequestConfig(name = "score", typeEnum = TypeEnum.INT, desc = "星星得分"),
        @RequestConfig(name = "receptionQuality", typeEnum = TypeEnum.CHAR_32, must = false, desc = "客服服务"),
        @RequestConfig(name = "problemSolve", typeEnum = TypeEnum.CHAR_32, must = false, desc = "问题处理"),
        @RequestConfig(name = "suggestion", typeEnum = TypeEnum.CHAR_32, must = false, desc = "意见反馈") },

        validateSession = true, response = false, group = ActionGroupNames.CUSTOMER, description = "玩家结束对话后，对刚才的客服做评价")
public class CustomerEvaluateReceptionServiceImpl implements Service {

    @InjectLocalService()
    private ReceptionEvaluateLocalService receptionEvaluateLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        // 获取输入参数 RequestConfig
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String customerId = parameterMap.get("customerId");

        String receptionId = parameterMap.get("receptionId");
        String suggestion = parameterMap.get("suggestion");
        int score = Integer.parseInt(parameterMap.get("score"));

        String receptionQuality = null;
        String problemSolve = null;

        // 评价服务得到低分时，进行服务态度和问题解决内容获取
        if (score <= FrontEndConfig.HIGH_SCORE_LIMIT) {
            if (score <= FrontEndConfig.LOW_SCORE_LIMIT && score >= 0) {
                receptionQuality = parameterMap.get("receptionQuality");
                problemSolve = parameterMap.get("problemSolve");
            }

            this.receptionEvaluateLocalService.insertReceptionEvaluate(
            customerId, receptionId, score, receptionQuality,
            problemSolve, suggestion);
            String serviceSid = SessionUtils
                    .createReceptionSessionId(receptionId);
            messageContext.success();
            //
            String responseMessage = messageContext.getResponseMessage();
            messageContext.push(serviceSid, responseMessage);
        }
        else {
            messageContext.setFlag(ResponseFlags.FAILURE_ERROR_DATA);
        }
    }
}
