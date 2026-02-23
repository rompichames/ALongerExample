package alongerexample;

import java.util.Calendar;
import java.util.Date;

public class Site {
    public Zone _zone;
    public Reading[] _readings = new Reading[1000];

    public Site(Zone zone) {
        _zone = zone;
    }

    public void addReading(Reading newReading) {
        int i = firstUnusedReadingsIndex();
        _readings[i] = newReading;
    }

    protected int firstUnusedReadingsIndex() {
        int i;
        for (i = 0; _readings[i] != null; i++);
        return i;
    }

    public int dayOfYear(Date arg) {
        Calendar cal =  Calendar.getInstance();
        cal.setTime(arg);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public double summerFraction(Date start, Date end) {
        if (start.after(_zone.summerEnd()) || end.before(_zone.summerStart()))
            return 0;
        else if (!start.before(_zone.summerStart()) && !start.after(_zone.summerEnd()) &&
                !end.before(_zone.summerStart()) && !end.after(_zone.summerEnd()))
            return 1;
        else {
            double summerDays;
            if (start.before(_zone.summerStart()) || start.after(_zone.summerEnd())) {
                // end is in the summer
                summerDays = dayOfYear(end) - dayOfYear (_zone.summerStart()) + 1;
            } else {
                // start is in summer
                summerDays = dayOfYear(_zone.summerEnd()) - dayOfYear (start) + 1;
            }
            return summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
        }
    }
}
