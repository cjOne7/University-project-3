package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {

    private static final DateTimeFormatter formatter
            = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy.M.d[ H'h']")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();

    private DateTimeUtil() {
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static LocalDateTime convert(String str) {
        return LocalDateTime.parse(str, DateTimeUtil.getFormatter());
    }

    public static String convert(LocalDateTime date) {
        return date.format(formatter);
    }
}
