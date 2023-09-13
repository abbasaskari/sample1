package com.something.riskmanagement.common.util;

import java.util.Collection;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public final class GeneralUtil {
    private GeneralUtil() {
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.equals(""));
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }
}
