public class Item {
    Point point;
    Character symbol;

    public Item(Point point, Character symbol) {
        this.point = point;
        this.symbol = symbol;
    }

    public Point getPoint() {
        return point;
    }

    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol( char symbol ) {
        this.symbol = symbol;
    }
}
