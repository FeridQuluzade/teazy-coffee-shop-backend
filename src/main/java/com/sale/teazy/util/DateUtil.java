package com.sale.teazy.util;

import com.sale.teazy.exception.DateNotParsingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;


@Service
public class DateUtil {
    private static final DateTimeFormatter DATE_FORMAT =
            new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss][.SSS]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MICRO_OF_SECOND, 0)
                    .toFormatter();

    public List<LocalDateTime> getDateIfAcceptable(String startDate, String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate, DATE_FORMAT);
            LocalDateTime end = LocalDateTime.parse(endDate, DATE_FORMAT);
            return Arrays.asList(start, end);
        } catch (Exception e) {
            throw new DateNotParsingException(startDate, endDate);
        }
    }
}
