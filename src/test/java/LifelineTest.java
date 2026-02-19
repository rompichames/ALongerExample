import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import alongerexample.LifelineSite;
import alongerexample.Reading;
import alongerexample.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LifelineTest { 
	
	LifelineSite _subject;
	private SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
	
	
	@BeforeEach
	public void setUp() throws ParseException {
		new Zone("A", 0.06, 0.07, _sdf.parse("15 May 2023"), _sdf.parse("10 Sep 2023")).register();
		new Zone("B", 0.07, 0.06, _sdf.parse("5 Jun 2023"), _sdf.parse("31 Aug 2023")).register();
		new Zone("C", 0.065, 0.065, _sdf.parse("5 Jun 2023"), _sdf.parse("31 Aug 2023")).register();
		_subject = new LifelineSite();
	}

	
	@Test
	public void testZero() throws ParseException {
		_subject.addReading(new Reading(10, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(10, _sdf.parse("1 Feb 2023")));
		assertEquals(0d, _subject.charge().amount());
	}
	
	
	@Test
	public void test100() throws ParseException {
		_subject.addReading(new Reading(10, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(110, _sdf.parse("1 Feb 2023")));
		assertEquals(4.84d, _subject.charge().amount());
	}
	
	
	@Test
	public void test99() throws ParseException {
		_subject.addReading(new Reading(100, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(199, _sdf.parse("1 Feb 2023")));
		assertEquals(4.79d, _subject.charge().amount());
	}
	
	
	@Test
	public void test101() throws ParseException {
		_subject.addReading(new Reading(1000, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(1101, _sdf.parse("1 Feb 2023")));
		assertEquals(4.91d, _subject.charge().amount());
	}
	
	
	@Test
	public void test199() throws ParseException {
		_subject.addReading(new Reading(10000, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(10199, _sdf.parse("1 Feb 2023")));
		assertEquals(11.6d, _subject.charge().amount());
	}
	
	
	@Test
	public void test200() throws ParseException {
		_subject.addReading(new Reading(0, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(200, _sdf.parse("1 Feb 2023")));
		assertEquals(11.68d, _subject.charge().amount());
	}
	
	
	@Test
	public void test201() throws ParseException {
		_subject.addReading(new Reading(50, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(251, _sdf.parse("1 Feb 2023")));
		assertEquals(11.77d, _subject.charge().amount());
	}
	
	
	@Test
	public void testMax() throws ParseException {
		_subject.addReading(new Reading(0, _sdf.parse("1 Jan 2023")));
		_subject.addReading(new Reading(Integer.MAX_VALUE, _sdf.parse("1 Feb 2023")));
		assertEquals (1.9730005336E8, _subject.charge().amount());
	}
	
	@Test
	public void testNoReadings() {
		try {
			_subject.charge();
			assert(false);
		} catch (NullPointerException e) {}
	}

}
