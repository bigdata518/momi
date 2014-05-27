package com.nd.momi;

import com.nd.momi.utils.SessionUtils;
import com.wolf.framework.config.FrameworkConfig;
import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
import com.wolf.framework.test.TestHandler;
import org.junit.AfterClass;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public abstract class AbstractMomiTest {

    protected final TestHandler testHandler;

    public AbstractMomiTest() {
        Map<String, String> parameterMap = new HashMap<String, String>(8, 1);
        parameterMap.put(FrameworkConfig.ANNOTATION_SCAN_PACKAGES, "com.nd.momi");
        parameterMap.put(FrameworkConfig.TASK_CORE_POOL_SIZE, "1");
        parameterMap.put(FrameworkConfig.TASK_MAX_POOL_SIZE, "2");
        //
        parameterMap.put(FrameworkConfig.REDIS_SERVER_HOST, "192.168.59.99");
        parameterMap.put(FrameworkConfig.REDIS_SERVER_PORT, "6379");
        parameterMap.put(FrameworkConfig.REDIS_MAX_POOL_SIZE, "1");
        parameterMap.put(FrameworkConfig.REDIS_MIN_POOL_SIZE, "2");
        parameterMap.put("file.path", "/data/file");
        this.testHandler = new TestHandler(parameterMap);
    }

    @AfterClass
    public static void tearDownClass() {
        ApplicationContext.CONTEXT.contextDestroyed();
    }

    public final void setReceptionSession(String userId) {
        String sid = SessionUtils.createReceptionSessionId(userId);
        Session session = new SessionImpl(sid);
        this.testHandler.setSession(session);
    }

    public final void setCustomerSession(String userId) {
        String sid = SessionUtils.createCustomerSessionId(userId);
        Session session = new SessionImpl(sid);
        this.testHandler.setSession(session);
    }
}
