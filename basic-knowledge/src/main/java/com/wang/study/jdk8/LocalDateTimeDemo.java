package com.wang.study.jdk8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @description: jdk1.8日期处理API
 * @author: WANG Y.G.
 * @time: 2019/11/02 14:51
 */
public class LocalDateTimeDemo {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now(); // 不包含具体时间的日期，比如2014-01-14。
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int dayOfMonth = localDate.getDayOfMonth();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(localDate + ":" + dayOfWeek);
        LocalDate of = LocalDate.of(2000, 05, 01); // 在java8中获取某个特定的日期
        System.out.println(localDate.equals(of)); // 在java8中检查两个日期是否相等
        System.out.println(of);
        LocalDate plus = localDate.plus(1, ChronoUnit.WEEKS); // 获取1周后的日期
        LocalTime localTime = LocalTime.now(); // 它代表的是不含日期的时间
        System.out.println(localTime);
        LocalTime plusHours = localTime.plusHours(2); // 增加时间里面的小时数
        System.out.println(plusHours);
        LocalDateTime localDateTime = LocalDateTime.now(); // 它包含了日期及时间，不过还是没有偏移信息或者说时区
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(); // 这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的。
        System.out.println(zonedDateTime);
        Clock clock = Clock.systemUTC();
        long millis = clock.millis();
        System.out.println(millis);
        System.out.println("检查是否是闰年：" + localDate.isLeapYear());
        LocalDate date = LocalDate.parse("20191102", DateTimeFormatter.BASIC_ISO_DATE); //日期解析
        System.out.println(date);
        try {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM-dd-yyyy"); //定义模板
            LocalDate parse = LocalDate.parse("11-02-2019", pattern); // 日期格式化
            System.out.println(parse);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * LocalDateTime转换为Date
     * @param localDateTime
     */
    public void localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId); // Combines this date-time with a time-zone to create a  ZonedDateTime.
        Date date = Date.from(zdt.toInstant());
        System.out.println(date.toString()); // Tue Mar 27 14:17:17 CST 2018
    }

    /**
     * Date转换为LocalDateTime
     * @param date
     */
    public void date2LocalDateTime(Date date){
        Instant instant = date.toInstant(); //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault(); //A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

        System.out.println(localDateTime.toString()); //2018-03-27T14:07:32.668
        System.out.println(localDateTime.toLocalDate() + " " +localDateTime.toLocalTime()); //2018-03-27 14:48:57.453

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //This class is immutable and thread-safe.@since 1.8
        System.out.println(dateTimeFormatter.format(localDateTime)); //2018-03-27 14:52:57
    }

    /**
     * 求两个LocalDateTime的时间差
     */
    public void getDuration() {
        LocalDateTime start = LocalDateTime.now();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toMillis());
    }

    /**
     * 获取某月份天数
     */
    public void getDaysOfMonth() {
        LocalDate time = LocalDate.of(2020, 2, 11);
        int days = time.lengthOfMonth();
//        int days = time.getMonth().length(time.isLeapYear());
        System.out.println(days);
    }
}
