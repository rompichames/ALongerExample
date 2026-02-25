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

    public int length() {
        long diff = _end.getTime() - _start.getTime();
        // Conversion millisecondes en jours + 1 pour inclure le dernier jour
        return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
    }

    public DateRange intersection(DateRange other) {
        if (this.disjoint(other)) return null;

        Date latestStart = _start.after(other.start()) ? _start : other.start();
        Date earliestEnd = _end.before(other.end()) ? _end : other.end();

        return new DateRange(latestStart, earliestEnd);
    }
}