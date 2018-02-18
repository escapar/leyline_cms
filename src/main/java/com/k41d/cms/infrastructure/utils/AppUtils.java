package com.k41d.cms.infrastructure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class AppUtils {
    public static double formatDouble2(Double d) {
        if (d == null){
            return d;
        }
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

    public static Date fromLocalDateTime(LocalDateTime ldt){
        return Date.from(ldt.toInstant(ZoneOffset.UTC));
    }
}
