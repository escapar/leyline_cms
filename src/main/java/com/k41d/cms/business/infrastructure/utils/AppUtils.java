package com.k41d.cms.business.infrastructure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AppUtils {
    public static double formatDouble2(Double d) {
        if (d == null){
            return d;
        }
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }
}
