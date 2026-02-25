package alongerexample;

import java.util.Calendar;
import java.util.Date;

public abstract class Site {
    public Zone _zone;
    public Reading[] _readings = new Reading[1000];
    protected static final double TAX_RATE = 0.05;
    protected static final double FUEL_CHARGE_RATE = 0.0175;

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

    public Dollars charge() {
        return charge(lastUsage(), lastReading().date(), nextDay(previousReading().date()));
    }

    protected Dollars fuelCharge(int usage) {
        return new Dollars(FUEL_CHARGE_RATE * usage);
    }

    protected Dollars taxes(Dollars amount) {
        return new Dollars(amount.times(TAX_RATE));
    }

    protected Dollars charge(int usage, Date start, Date end) {
        Dollars result;
        result = baseCharge(usage, start, end);
        result = result.plus(taxes(result));
        result = result.plus(fuelCharge(usage));
        result = result.plus(fuelChargeTaxes(usage));
        return result;
    }

    protected abstract Dollars baseCharge(int usage, Date start, Date end);
    protected abstract Dollars fuelChargeTaxes(int usage);



    protected Reading lastReading() {
        return _readings[firstUnusedReadingsIndex() - 1];
    }

    protected Reading previousReading() {
        return _readings[firstUnusedReadingsIndex() - 2];
    }

    protected int lastUsage() {
        return lastReading().amount() - previousReading().amount();
    }

    protected Date nextDay(Date date) {
        Calendar cal =  Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }
}
