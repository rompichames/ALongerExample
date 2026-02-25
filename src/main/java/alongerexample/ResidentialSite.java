package alongerexample;

import java.util.Date;

public class ResidentialSite extends Site{

	public ResidentialSite (Zone zone) {
		super(zone);
	}

	@Override
	protected Dollars baseCharge(Date start, Date end) {
		double summerFraction = summerFraction(start, end);
		int usage = lastUsage();
		return new Dollars((usage * _zone.summerRate() * summerFraction) +
				(usage * _zone.winterRate() * (1 - summerFraction)));
	}

	protected Dollars fuelChargeTaxes() {
		return taxes(fuelCharge());
	}
}
