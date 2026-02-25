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

    public double summerFraction() {
        DateRange period = lastPeriod();
        Date start = period.start();
        Date end = period.end();

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

    protected Dollars fuelCharge() {
        return new Dollars(FUEL_CHARGE_RATE * lastUsage());
    }

    protected Dollars taxes(Dollars amount) {
        return new Dollars(amount.times(TAX_RATE));
    }

    public Dollars charge() {
        return baseCharge()
                .plus(taxes(baseCharge()))
                .plus(fuelCharge())
                .plus(fuelChargeTaxes());
    }

    protected abstract Dollars baseCharge();
    protected abstract Dollars fuelChargeTaxes();

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

    public DateRange lastPeriod() {
        return new DateRange(nextDay(previousReading().date()), lastReading().date());
    }
}
