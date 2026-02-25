package alongerexample;

import java.util.Date;

public class ResidentialSite extends Site{

	public ResidentialSite (Zone zone) {
		super(zone);
	}

	public Dollars charge(int usage, Date start, Date end) {
		double summerFraction = summerFraction(start, end);

		Dollars base = new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));

		Dollars result = base.plus(taxes(base));
		result = result.plus(fuelCharge(usage));
		result = result.plus(fuelChargeTaxes(usage));

		return result;
	}

	protected Dollars fuelChargeTaxes(int usage) {
		return taxes(fuelCharge(usage));
	}
}
