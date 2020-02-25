package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author kasi0004
 */
public final class DateUtil {

    private static final DateTimeFormatter formatter
            = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy.MM.dd")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();

    private DateUtil() {
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static LocalDate convert(String str) {
        return LocalDateTime.parse(str, DateUtil.getFormatter()).toLocalDate();
    }

}
