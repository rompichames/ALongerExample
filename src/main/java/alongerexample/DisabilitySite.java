package alongerexample;

import java.util.Date;

public class DisabilitySite extends Site{

	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		super(zone);
	}

	@Override
	protected Dollars baseCharge() {
		double summerFraction = summerFraction();
		int usage = lastUsage();
		int minUsage = Math.min(usage, CAP);

		Dollars base = new Dollars((minUsage * _zone.summerRate() * summerFraction) +
				(minUsage * _zone.winterRate() * (1 - summerFraction)));

		return base.plus(new Dollars(Math.max(usage - CAP, 0) * 0.062));
	}

	@Override
	protected Dollars fuelChargeTaxes() {
		return taxes(fuelCharge()).min(FUEL_TAX_CAP);
	}
}
