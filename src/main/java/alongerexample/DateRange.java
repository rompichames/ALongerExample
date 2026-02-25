package alongerexample;

import java.util.Date;

public class DateRange {
    private final Date _start;
    private final Date _end;

    public DateRange(Date start, Date end) {
        _start = start;
        _end = end;
    }

    public Date start() { return _start; }
    public Date end() { return _end; }
}