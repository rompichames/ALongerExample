import alongerexample.BusinessSite;
import alongerexample.Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessTest {
    BusinessSite _businessSite;

    @BeforeEach
    public void setup() {
        _businessSite = new BusinessSite();
    }

    //null
    @Test @SuppressWarnings("deprecation")
    public void     testZero() {
        _businessSite.addReading(new Reading(10,new Date("1 Jan 2023")));
        _businessSite.addReading(new Reading(10,new Date("1 Feb 2023")));
        assertEquals(0d, _businessSite.charge().amount());
    }

    //under 50 threshold
    @Test @SuppressWarnings("deprecation")
    public void testLow() {
        _businessSite.addReading(new Reading(100,new Date("1 Jan 2023")));
        _businessSite.addReading(new Reading(200,new Date("1 Feb 2023")));
        assertEquals(7.27d, _businessSite.charge().amount());
    }

    //between 50 and 75
    @Test @SuppressWarnings("deprecation")
    public void test50Threshold() {
        _businessSite.addReading(new Reading(100,new Date("1 Jan 2023")));
        _businessSite.addReading(new Reading(700,new Date("1 Feb 2023")));
        assertEquals(43.36d, _businessSite.charge().amount());
    }

    //above 75 threshold
    @Test @SuppressWarnings("deprecation")
    public void test75Threshold() {
        _businessSite.addReading(new Reading(100,new Date("1 Jan 2023")));
        _businessSite.addReading(new Reading(900,new Date("1 Feb 2023")));
        assertEquals(57.75d, _businessSite.charge().amount());
    }

    //
    @Test @SuppressWarnings("deprecation")
    public void testHigh() {
        _businessSite.addReading(new Reading(100,new Date("1 Jan 2023")));
        _businessSite.addReading(new Reading(3000,new Date("1 Feb 2023")));
        assertEquals(206.79d, _businessSite.charge().amount());
    }
}
