import static org.junit.jupiter.api.Assertions.*;

import alongerexample.Dollars;
import org.junit.jupiter.api.Test;

class DollarsTest {

	@Test
	void testDollarsDouble() {
		assertAll("Conversions",
				() -> { Dollars dollar = new Dollars(1.666d); assertEquals(1.67d, dollar.amount());},
				() -> { Dollars dollar = new Dollars(1.664d); assertEquals(1.66d, dollar.amount());}
				);
	}

	@Test
	void testPlusDollars() {
		Dollars dollar = new Dollars(10.23d);
		assertEquals(21.57d, dollar.plus(new Dollars(11.34d)).amount());
	}

	@Test
	void testPlusDouble() {
		Dollars dollar = new Dollars(10.23d);
		assertEquals(21.57d, dollar.plus(11.34d).amount());
	}

	@Test
	void testMinusDollars() {
		Dollars dollar = new Dollars(10.23d);
		assertEquals(-1.11d, dollar.minus(new Dollars(11.34d)).amount());
	}

	@Test
	void testMinusDouble() {
		Dollars dollar = new Dollars(10.23d);
		assertEquals(-1.11d, dollar.minus(11.34d).amount());
	}

	@Test
	void testTimesDouble() {
		Dollars dollar = new Dollars(10.23d);
		assertEquals(116,01d, dollar.times(11.34d).amount());
	}

	@Test
	void testMin() {
		assertEquals(1.66d, new Dollars(1.666d).min(new Dollars(1.664d)).amount());
	}
	
	@Test
	void testMax() {
		assertEquals(1.67d, new Dollars(1.666d).max(new Dollars(1.664d)).amount());
	}

	@Test
	void testIsGreaterThan() {
		assertAll("Comparaisons de deux dollars",
				() -> assertTrue(new Dollars(1.666d).isGreaterThan(new Dollars(1.664d))),
				() -> assertFalse(new Dollars(1.664d).isGreaterThan(new Dollars(1.666d)))
				);
	}

}
