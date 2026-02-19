import alongerexample.BusinessSite;
import alongerexample.Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessTest {
    BusinessSite _businessSite;
    private SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US); 
    
    @BeforeEach
    public void setup() {
        _businessSite = new BusinessSite();
    }

    //null
    @Test 
    public void     testZero() throws ParseException {
        _businessSite.addReading(new Reading(10, _sdf.parse("1 Jan 2023")));
        _businessSite.addReading(new Reading(10, _sdf.parse("1 Feb 2023")));
        assertEquals(0d, _businessSite.charge().amount());
    }

    //under 50 threshold
    @Test 
    public void testLow() throws ParseException {
        _businessSite.addReading(new Reading(100, _sdf.parse("1 Jan 2023")));
        _businessSite.addReading(new Reading(200, _sdf.parse("1 Feb 2023")));
        assertEquals(7.27d, _businessSite.charge().amount());
    }

    //between 50 and 75
    @Test 
    public void test50Threshold() throws ParseException {
        _businessSite.addReading(new Reading(100, _sdf.parse("1 Jan 2023")));
        _businessSite.addReading(new Reading(700, _sdf.parse("1 Feb 2023")));
        assertEquals(43.36d, _businessSite.charge().amount());
    }

    //above 75 threshold
    @Test 
    public void test75Threshold() throws ParseException {
        _businessSite.addReading(new Reading(100, _sdf.parse("1 Jan 2023")));
        _businessSite.addReading(new Reading(900, _sdf.parse("1 Feb 2023")));
        assertEquals(57.75d, _businessSite.charge().amount());
    }

    //
    @Test 
    public void testHigh() throws ParseException {
        _businessSite.addReading(new Reading(100, _sdf.parse("1 Jan 2023")));
        _businessSite.addReading(new Reading(3000, _sdf.parse("1 Feb 2023")));
        assertEquals(206.79d, _businessSite.charge().amount());
    }
}
