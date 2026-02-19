import alongerexample.DisabilitySite;
import alongerexample.Reading;
import alongerexample.ResidentialSite;
import alongerexample.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResidentialSiteTest {
    ResidentialSite _residentialSite;
    Zone _zone;

    @BeforeEach
    public void setUp()
    {
        _zone = new Zone("A", 0.06, 0.07,
                new Date("1 Jun 2024"),
                new Date("31 Aug 2024"));
        _residentialSite = new ResidentialSite(_zone);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testOutSummer(){
        _residentialSite.addReading(new Reading(100, new Date("1 Jan 2024")));
        _residentialSite.addReading(new Reading(200, new Date("31 Jan 2024")));
        assertEquals(9.19d, _residentialSite.charge().amount());
    }

    @Test @SuppressWarnings("deprecation")
    void testInSummer()
    {
        _residentialSite.addReading(new Reading(100, new Date("1 Jul 2024")));
        _residentialSite.addReading(new Reading(200, new Date("31 Jul 2024")));
        assertEquals(8.14d, _residentialSite.charge().amount());
    }

    @Test @SuppressWarnings("deprecation")
    void testOverCap() {
        _residentialSite.addReading(new Reading(100, new Date("1 Jan 2024")));
        _residentialSite.addReading(new Reading(400, new Date("31 Jan 2024"))); // Usage 300 (> 200)
        assertEquals(27.56d, _residentialSite.charge().amount());
    }

    @Test @SuppressWarnings("deprecation")
    void testPartialSummer() {
        // mai → hiver juin → été
        _residentialSite.addReading(new Reading(100, new Date("25 May 2024")));
        _residentialSite.addReading(new Reading(200, new Date("5 Jun 2024")));
        assertEquals(8.72d, _residentialSite.charge().amount());
    }

}
