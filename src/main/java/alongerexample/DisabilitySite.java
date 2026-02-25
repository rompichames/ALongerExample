package alongerexample;

import java.util.Calendar;
import java.util.Date;

public class DisabilitySite extends Site{

	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final double TAX_RATE = 0.05;
	private static final Dollars CAP = new Dollars(200);

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

		// calcul du montant de base (été vs hiver)
		Dollars base = new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));

		Dollars limitedBase = base.min(CAP);

		// calcul taxe sur montant plafonné
		Dollars tax = new Dollars(limitedBase.times(TAX_RATE));

		// calcul du fuel et de sa taxe avec son plafond
		Dollars fuel = new Dollars(usage * 0.0175);
		Dollars fuelTax = new Dollars(fuel.times(TAX_RATE)).min(FUEL_TAX_CAP);

		return limitedBase.plus(tax).plus(fuel).plus(fuelTax);
	}
}
