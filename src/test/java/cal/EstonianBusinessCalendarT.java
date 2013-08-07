package cal;

import org.joda.time.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class EstonianBusinessCalendarT {

    private BusinessCalendar ebc = new EstonianBusinessCalendar();

    @Test
    public void isHolidayT() {
        //static holidays
        assertTrue("New Year's Day", ebc.isHoliday(new LocalDate(2016, 1, 1)));
        assertTrue("Independence Day", ebc.isHoliday(new LocalDate(2017, 2, 24)));
        assertTrue("Spring Day", ebc.isHoliday(new LocalDate(2019, 5, 1)));
        assertTrue("Victory Day", ebc.isHoliday(new LocalDate(2015, 6, 23)));
        assertTrue("St. John's Day", ebc.isHoliday(new LocalDate(2014, 6, 24)));
        assertTrue("Day of Restoration of Independence", ebc.isHoliday(new LocalDate(2018, 8, 20)));
        assertTrue("Christmas Eve", ebc.isHoliday(new LocalDate(2013, 12, 24)));
        assertTrue("Christmas Day", ebc.isHoliday(new LocalDate(2014, 12, 25)));
        assertTrue("Boxing Day", ebc.isHoliday(new LocalDate(2015, 12, 26)));

        //moving holidays
        assertTrue("Pentecost", ebc.isHoliday(new LocalDate(2016, 5, 15)));
        assertTrue("Easter Sunday", ebc.isHoliday(new LocalDate(2015, 4, 5)));
        assertTrue("Good Friday 2013", ebc.isHoliday(new LocalDate(2013, 3, 29)));
        assertTrue("Good Friday 2014", ebc.isHoliday(new LocalDate(2014, 4, 18)));
        assertTrue("Good Friday 2017", ebc.isHoliday(new LocalDate(2017, 4, 14)));
    }

    @Test
    public void isBusinessDayT() {
        assertTrue("Monday", ebc.isBusinessDay(new LocalDate(2013, 8, 5)));
        assertTrue("Friday", ebc.isBusinessDay(new LocalDate(2013, 8, 9)));
        assertFalse("Saturday", ebc.isBusinessDay(new LocalDate(2013, 8, 10)));
        assertFalse("Sunday", ebc.isBusinessDay(new LocalDate(2013, 8, 11)));
    }

    @Test
    public void isDaylightPeriodT() {
        LocalDateTime day = new LocalDateTime(2013, 8, 7, 0, 0);
        assertFalse("Summer business day midnight", ebc.isDaylightPeriod(day));
        assertFalse("Summer business day 7.59", ebc.isDaylightPeriod(day.plusHours(7).plusMinutes(59)));
        assertTrue("Summer business day 8.00", ebc.isDaylightPeriod(day.plusHours(8)));
        assertTrue("Summer business day 14.00", ebc.isDaylightPeriod(day.plusHours(14)));
        assertTrue("Summer business day 23.59", ebc.isDaylightPeriod(day.plusHours(23).plusMinutes(59)));

        day = new LocalDateTime(2013, 8, 10, 0 ,0);
        assertFalse("Weekend during daytime", ebc.isDaylightPeriod(day.plusHours(15)));

        day = new LocalDateTime(2013, 8, 20, 0, 0);
        assertFalse("Holiday during daytime", ebc.isDaylightPeriod(day.plusHours(16)));

        day = new LocalDateTime(2014, 2, 18, 0 ,0);
        assertFalse("Winter business day 6.59", ebc.isDaylightPeriod(day.plusHours(6).plusMinutes(59)));
        assertTrue("Winter business day 7.00", ebc.isDaylightPeriod(day.plusHours(7)));
        assertTrue("Winter business day 22.59", ebc.isDaylightPeriod(day.plusHours(22).plusMinutes(59)));
        assertFalse("Winter business day 23.00", ebc.isDaylightPeriod(day.plusHours(23)));

        DateTimeZone dtz = ebc.getDateTimeZone();
        for (Map.Entry<LocalDate, LocalDate> startEnd: getTimeZoneStartEnds().entrySet()) {
            DateTime start = startEnd.getKey().toDateTime(new LocalTime(0, 0), dtz);
            String help = "Going to daylight saving time, 7.59";
            assertFalse(help, ebc.isDaylightPeriod(start.plusHours(23 + 7).plusMinutes(59).toLocalDateTime()));

            help = "Going to daylight saving time, 8.00";
            assertTrue(help, ebc.isDaylightPeriod(start.plusHours(23 + 8).toLocalDateTime()));

            DateTime end = startEnd.getValue().toDateTime(new LocalTime(0, 0), dtz);
            help = "Coming from daylight saving time, 6.59";
            assertFalse(help, ebc.isDaylightPeriod(end.plusHours(25 + 6).plusMinutes(59).toLocalDateTime()));

            help = "Coming from daylight saving time, 7.00";
            assertTrue(help, ebc.isDaylightPeriod(end.plusHours(25 + 7).toLocalDateTime()));
        }
    }

    private HashMap<LocalDate, LocalDate> getTimeZoneStartEnds() {
        HashMap<LocalDate, LocalDate> tzs = new HashMap<LocalDate, LocalDate>();
        tzs.put(new LocalDate(2010, 3, 28), new LocalDate(2010, 10, 31));
        tzs.put(new LocalDate(2011, 3, 27), new LocalDate(2011, 10, 30));
        tzs.put(new LocalDate(2012, 3, 25), new LocalDate(2012, 10, 28));
        tzs.put(new LocalDate(2013, 3, 31), new LocalDate(2013, 10, 27));
        tzs.put(new LocalDate(2014, 3, 30), new LocalDate(2014, 10, 26));
        tzs.put(new LocalDate(2015, 3, 29), new LocalDate(2015, 10, 25));
        tzs.put(new LocalDate(2016, 3, 27), new LocalDate(2016, 10, 30));
        tzs.put(new LocalDate(2017, 3, 26), new LocalDate(2017, 10, 29));
        tzs.put(new LocalDate(2018, 3, 25), new LocalDate(2018, 10, 28));
        tzs.put(new LocalDate(2019, 3, 31), new LocalDate(2019, 10, 27));
        return tzs;
    }

}
