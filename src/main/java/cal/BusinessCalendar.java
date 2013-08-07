package cal;

import de.jollyday.HolidayManager;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public abstract class BusinessCalendar {

    private final DateTimeZone dateTimeZone;

    public DateTimeZone getDateTimeZone() {
        return dateTimeZone;
    }

    protected final HolidayManager holidayManager;

    public BusinessCalendar(DateTimeZone dtz) {
        this.dateTimeZone = dtz;
        this.holidayManager = createHolidayManager();
    }

    protected abstract HolidayManager createHolidayManager();

    public boolean isHoliday(LocalDate ld) {
        return holidayManager.isHoliday(ld);
    }

    public boolean isBusinessDay(LocalDate ld) {
        int dayOfWeek = ld.getDayOfWeek();
        if (dayOfWeek == DateTimeConstants.SATURDAY || dayOfWeek == DateTimeConstants.SUNDAY)
            return false;
        else if (isHoliday(ld))
            return false;
        else
            return true;
    }

    protected abstract boolean isBetweenWorkingHours(LocalDateTime ldt);

    public abstract boolean isDaylightPeriod(LocalDateTime ldt);
}
