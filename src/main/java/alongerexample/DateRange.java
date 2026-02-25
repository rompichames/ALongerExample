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

    public boolean disjoint(DateRange other) {
        return _start.after(other.end()) || _end.before(other.start());
    }

    public boolean contains(DateRange other) {
        return (!other.start().before(_start)) && (!other.end().after(_end));
    }
}