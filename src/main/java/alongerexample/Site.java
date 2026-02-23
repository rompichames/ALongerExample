package alongerexample;

public class Site {
    public Zone _zone;
    public Reading[] _readings = new Reading[1000];

    public Site(Zone zone) {
        _zone = zone;
    }

    public void addReading(Reading newReading) {
        int i = firstUnusedReadingsIndex();
        _readings[i] = newReading;
    }

    public int firstUnusedReadingsIndex() {
        int i;
        for (i = 0; _readings[i] != null; i++);
        return i;
    }
}
