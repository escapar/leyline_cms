package org.escapar.cms.infrastructure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.function.Function;

public class AppUtils {
    public static double formatDouble2(Double d) {
        if (d == null){
            return d;
        }
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

    public static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception ex) {
                return ThrowingFunction.sneakyThrow(ex);
            }
        };
    }

    public static Date fromZonedDateTime(ZonedDateTime ldt){
        return Date.from(ldt.toInstant());
    }
}
