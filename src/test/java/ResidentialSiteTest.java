import alongerexample.DisabilitySite;
import alongerexample.Reading;
import alongerexample.ResidentialSite;
import alongerexample.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResidentialSiteTest {
    ResidentialSite _residentialSite;
    Zone _zone;
    private SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US   );

    @BeforeEach
    public void setUp() throws ParseException {
        _zone = new Zone("A", 0.06, 0.07,
                _sdf.parse("1 Jun 2024"),
                _sdf.parse("31 Aug 2024"));
        _residentialSite = new ResidentialSite(_zone);
    }

    @Test
    void testOutSummer() throws ParseException {
        _residentialSite.addReading(new Reading(100, _sdf.parse("1 Jan 2024")));
        _residentialSite.addReading(new Reading(200, _sdf.parse("31 Jan 2024")));
        assertEquals(9.19d, _residentialSite.charge().amount());
    }

    @Test
    void testInSummer() throws ParseException {
        _residentialSite.addReading(new Reading(100, _sdf.parse("1 Jul 2024")));
        _residentialSite.addReading(new Reading(200, _sdf.parse("31 Jul 2024")));
        assertEquals(8.14d, _residentialSite.charge().amount());
    }

    @Test
    void testOverCap() throws ParseException {
        _residentialSite.addReading(new Reading(100, _sdf.parse("1 Jan 2024")));
        _residentialSite.addReading(new Reading(400, _sdf.parse("31 Jan 2024"))); // Usage 300 (> 200)
        assertEquals(27.56d, _residentialSite.charge().amount());
    }

    @Test
    void testPartialSummer() throws ParseException {
        // mai → hiver juin → été
        _residentialSite.addReading(new Reading(100, _sdf.parse("25 May 2024")));
        _residentialSite.addReading(new Reading(200, _sdf.parse("5 Jun 2024")));
        assertEquals(9.19d, _residentialSite.charge().amount());
    }

}
