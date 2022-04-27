public record Point(int x, int y) {
    Point(Point a, Point b) {
        this(a.x() + b.x(), a.y() + b.y());
    }

    public double distanceTo(Point that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
