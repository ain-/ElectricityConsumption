package cons;

import cal.EstonianBusinessCalendar;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ConsumptionCounterT {

    private EstonianBusinessCalendar ebc = new EstonianBusinessCalendar();

    @Test
    public void totalConsumptionsT() {
        AbstractMap<Integer, Integer> hourKW = new HashMap<Integer, Integer>();
        hourKW.put(1, 2);
        hourKW.put(2, 3);
        hourKW.put(26, 3);
        hourKW.put(32, 6);
        hourKW.put(744, 0);
        ConsumptionCounter cc = new ConsumptionCounter(2012, 1, ebc, hourKW);
        assertEquals("Example data, day consumption", 6, cc.getTotalDayConsumption());
        assertEquals("Example data, night consumption", 8, cc.getTotalNightConsumption());

        hourKW = new HashMap<Integer, Integer>();
        hourKW.put(26 * 24 + 25 + 7, 7);
        hourKW.put(26 * 24 + 25 + 8, 5);
        cc = new ConsumptionCounter(2013, 10, ebc, hourKW);
        assertEquals("Coming from daylight change month, day consumption", 5, cc.getTotalDayConsumption());
        assertEquals("Coming from daylight change month, night consumption", 7, cc.getTotalNightConsumption());

        hourKW = new HashMap<Integer, Integer>();
        hourKW.put(28 * 24 + 23 + 8, 6);
        hourKW.put(28 * 24 + 23 + 9, 3);
        cc = new ConsumptionCounter(2015, 3, ebc, hourKW);
        assertEquals("Going to daylight change month, day consumption", 3, cc.getTotalDayConsumption());
        assertEquals("Going to daylight change month, night consumption", 6, cc.getTotalNightConsumption());
    }

}
