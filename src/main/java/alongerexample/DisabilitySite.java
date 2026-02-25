package alongerexample;

import java.util.Calendar;
import java.util.Date;

public class DisabilitySite extends Site{

	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
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

	@Override
	protected Dollars charge(int usage, Date start, Date end) {
		// calcul de la fraction d'été
		double summerFraction = summerFraction(start, end);
		int minUsage = Math.min(usage, CAP);

		// calcul du montant de base (été vs hiver)
		Dollars base = new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));

		base = base.plus(new Dollars(Math.max(usage - minUsage, 0) * 0.062));

		// calcul du fuel et de sa taxe avec son plafond
		base = base.plus(taxes(base));
		base = base.plus(fuelCharge(usage));
		base = base.plus(fuelChargeTaxes(usage));

		return base;
	}

	protected Dollars fuelChargeTaxes(int usage) {
		return new Dollars(fuelCharge(usage).times(TAX_RATE).min(FUEL_TAX_CAP));
	}
}
