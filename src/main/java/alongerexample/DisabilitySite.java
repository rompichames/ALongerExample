package alongerexample;

import java.util.Calendar;
import java.util.Date;

public class DisabilitySite extends Site{

	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final double TAX_RATE = 0.05;
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		super(zone);
	}

	public Dollars charge() {
		int i;
		for (i = 0; _readings[i] != null; i++);
		int usage = _readings[i-1].amount() - _readings[i-2].amount();
		Date end = _readings[i-1].date();
		Calendar cal =  Calendar.getInstance();
		cal.setTime(end);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date start = cal.getTime();
		return charge(usage, start, end);
	}

	private Dollars charge(int fullUsage, Date start, Date end) {
		Dollars result;
		double summerFraction;
		int usage = Math.min(fullUsage, CAP);
		if (start.after(_zone.summerEnd()) || end.before(_zone.summerStart()))
			summerFraction = 0;
		else if (!start.before(_zone.summerStart()) && !start.after(_zone.summerEnd()) &&
				!end.before(_zone.summerStart()) && !end.after(_zone.summerEnd()))
			summerFraction = 1;
		else {
			double summerDays;
			if (start.before(_zone.summerStart()) || start.after(_zone.summerEnd())) {
				// end is in the summer
				summerDays = dayOfYear(end) - dayOfYear (_zone.summerStart()) + 1;
			} else {
				// start is in summer
				summerDays = dayOfYear(_zone.summerEnd()) - dayOfYear (start) + 1;
			};
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		};
		result = new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars(Math.max(fullUsage - usage, 0) * 0.062));
		result = result.plus(new Dollars(result.times(TAX_RATE)));
		Dollars fuel = new Dollars(fullUsage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars(result.plus(fuel.times(TAX_RATE).min(FUEL_TAX_CAP)));
		return result;
	}
	
	private int dayOfYear(Date arg) {
		Calendar cal =  Calendar.getInstance();
		cal.setTime(arg);
		return cal.get(Calendar.DAY_OF_YEAR);
	}


}
