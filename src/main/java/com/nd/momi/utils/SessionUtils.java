package com.nd.momi.utils;

/**
 *
 * @author jianying9
 */
public class SessionUtils {

    private final static String RECEPTION_PREFIX = "s_";
    private final static String CUSTOMER_PREFIX = "c_";

    public static String createReceptionSessionId(String userId) {
        return RECEPTION_PREFIX.concat(userId);
    }

    public static String getReceptionIdFromSessionId(String sid) {
        String userId = "-1";
        int index = sid.indexOf(RECEPTION_PREFIX);
        if (index == 0) {
            userId = sid.substring(index + RECEPTION_PREFIX.length());
        }
        return userId;
    }

    public static boolean isReceptionSession(String sid) {
        int index = sid.indexOf(RECEPTION_PREFIX);
        return index == 0;
    }

    public static String createCustomerSessionId(String userId) {
        return CUSTOMER_PREFIX.concat(userId);
    }

    public static String getCustomerIdFromSessionId(String sid) {
        String userId = "-1";
        int index = sid.indexOf(CUSTOMER_PREFIX);
        if (index == 0) {
            userId = sid.substring(index + CUSTOMER_PREFIX.length());
        }
        return userId;
    }
}
