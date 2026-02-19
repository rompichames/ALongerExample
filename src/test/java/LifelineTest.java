import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import alongerexample.LifelineSite;
import alongerexample.Reading;
import alongerexample.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LifelineTest { 
	
	LifelineSite _subject;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		new Zone("A", 0.06, 0.07, new Date ("15 May 2023"), new Date ("10 Sep 2023")).register();
		new Zone("B", 0.07, 0.06, new Date ("5 Jun 2023"), new Date ("31 Aug 2023")).register();
		new Zone("C", 0.065, 0.065, new Date ("5 Jun 2023"), new Date ("31 Aug 2023")).register();
		_subject = new LifelineSite();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testZero() {
		_subject.addReading(new Reading(10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(10, new Date ("1 Feb 2023")));
		assertEquals(0d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test100() {
		_subject.addReading(new Reading(10, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(110, new Date ("1 Feb 2023")));
		assertEquals(4.84d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test99() {
		_subject.addReading(new Reading(100, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(199, new Date ("1 Feb 2023")));
		assertEquals(4.79d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test101() {
		_subject.addReading(new Reading(1000, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(1101, new Date ("1 Feb 2023")));
		assertEquals(4.91d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test199() {
		_subject.addReading(new Reading(10000, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(10199, new Date ("1 Feb 2023")));
		assertEquals(11.6d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test200() {
		_subject.addReading(new Reading(0, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(200, new Date ("1 Feb 2023")));
		assertEquals(11.68d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test201() {
		_subject.addReading(new Reading(50, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(251, new Date ("1 Feb 2023")));
		assertEquals(11.77d, _subject.charge().amount());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testMax() {
		_subject.addReading(new Reading(0, new Date ("1 Jan 2023")));
		_subject.addReading(new Reading(Integer.MAX_VALUE, new Date ("1 Feb 2023")));
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
