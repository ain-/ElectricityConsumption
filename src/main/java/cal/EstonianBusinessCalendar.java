package cal;

import de.jollyday.HolidayManager;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.net.MalformedURLException;
import java.net.URL;

public class EstonianBusinessCalendar extends BusinessCalendar {

    public EstonianBusinessCalendar() {
        super(DateTimeZone.forID("Europe/Tallinn"));
    }

    @Override
    protected HolidayManager createHolidayManager() {
        URL location = null;
        try {
            location = new URL("file:src/main/resources/Holidays_ee_fixed.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return HolidayManager.getInstance(location);
    }

    @Override
    protected boolean isBetweenWorkingHours(LocalDateTime ldt) {
        int hour = ldt.getHourOfDay();
        boolean isDaylightTime = getDateTimeZone().toTimeZone().inDaylightTime(ldt.toDate());
        if (isDaylightTime)
            return (hour >= 8);
        else
            return (7 <= hour && hour <= 22);
    }

    @Override
    public boolean isDaylightPeriod(LocalDateTime ldt) {
        if (isBusinessDay(ldt.toLocalDate()))
            return isBetweenWorkingHours(ldt);
        else
            return false;
    }

}
