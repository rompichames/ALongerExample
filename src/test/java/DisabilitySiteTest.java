import alongerexample.DisabilitySite;
import alongerexample.Reading;
import alongerexample.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisabilitySiteTest {
    DisabilitySite _disabilitySite;
    Zone _zone;
    private SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    @BeforeEach
    public void setUp() throws ParseException {
        _zone = new Zone("A", 0.06, 0.07,
                _sdf.parse("1 Jun 2024"),
                _sdf.parse("31 Aug 2024"));
        _disabilitySite = new DisabilitySite(_zone);
    }

    @Test
    void testOutSummer() throws ParseException {
        _disabilitySite.addReading(new Reading(100, _sdf.parse("1 Jan 2024")));
        _disabilitySite.addReading(new Reading(200, _sdf.parse("31 Jan 2024")));
        assertEquals(9.19d, _disabilitySite.charge().amount());
    }

    @Test
    void testInSummer() throws ParseException {
        _disabilitySite.addReading(new Reading(100, _sdf.parse("1 Jul 2024")));
        _disabilitySite.addReading(new Reading(200, _sdf.parse("31 Jul 2024")));
        assertEquals(8.14d, _disabilitySite.charge().amount());
    }

    @Test
    void testOverCap() throws ParseException {
        _disabilitySite.addReading(new Reading(100, _sdf.parse("1 Jan 2024")));
        _disabilitySite.addReading(new Reading(400, _sdf.parse("31 Jan 2024"))); // Usage 300 (> 200)
        assertEquals(33.91d, _disabilitySite.charge().amount());
    }

}
