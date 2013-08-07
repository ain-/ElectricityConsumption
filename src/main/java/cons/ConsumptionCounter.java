package cons;

import cal.BusinessCalendar;
import org.joda.time.DateTime;

import java.util.*;

public class ConsumptionCounter {

    private int year;
    private int month;
    private AbstractMap<Integer, Integer> hourKW;
    private BusinessCalendar workCal;

    /**
     *
     * @param month 1-12
     */
    public ConsumptionCounter(int year, int month, BusinessCalendar workCal, AbstractMap<Integer, Integer> hourKW) {
        this.year = year;
        this.month = month;
        this.hourKW = hourKW;
        this.workCal = workCal;
        calculateConsumptions();
    }

    private int totalDayConsumption = 0;
    private int totalNightConsumption = 0;

    private void calculateConsumptions() {
        for (Map.Entry<Integer, Integer> hrKw: hourKW.entrySet()) {
            int hour = hrKw.getKey() - 1;
            int kw = hrKw.getValue();
            DateTime dt = new DateTime(year, month, 1, 0, 0, workCal.getDateTimeZone()).plusHours(hour);
            if (workCal.isDaylightPeriod(dt.toLocalDateTime()))
                totalDayConsumption += kw;
            else
                totalNightConsumption += kw;
        }
    }

    public int getTotalDayConsumption() {
       return totalDayConsumption;
    }

    public int getTotalNightConsumption() {
        return totalNightConsumption;
    }

}
