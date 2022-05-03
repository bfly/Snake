import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class GameManager {
    public static final char EMPTY = ' ';
    public static final char WALL = 'W';
    public static final char SNAKE = '$';
    public static final char FOOD = '*';
    public static final int NO_OF_SNAKES = 1;
    static final int NO_OF_FOOD = 10;
    static final boolean seeded = false;

    Scanner kbd;

    Item item;
    Point snakeHead, snakeTail;
    Queue<Point> snake;
    List<Point> foodLocs = new ArrayList<>();
    List<Double> foodDist = new ArrayList<>();

    Item[][] items;
    SnakeGUI gui;

    private void go(int food) {
        playGame(food);
    }

    private void initMaze( String fn) {
        Item[][] maze;
        Scanner in;
        int height, width, x1, x2, y1, y2;

        try {
            in = new Scanner(new File(fn));
            height = in.nextInt();
            width = in.nextInt();
            in.nextLine();
        } catch (FileNotFoundException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
        maze = new Item[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze[row][col] = new Empty(new Point(row, col), EMPTY);
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze[row][col] = new Empty(new Point(row, col), EMPTY);
            }
        }
        while (in.hasNextLine()) {
            String [] fields = in.nextLine().split(" ");
            if (fields.length == 4) {
                try {
                    x1 = Integer.parseInt(fields[0]);
                    y1 = Integer.parseInt(fields[1]);
                    x2 = Integer.parseInt(fields[2]);
                    y2 = Integer.parseInt(fields[3]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
                for ( int row = x1; row <= x2; row++ ) {
                    for ( int col = y1; col <= y2; col++ ) {
                        Point point = new Point(row, col);
                        maze[row][col] = new Wall(point, WALL);
                    }
                }
            }
        }
        in.close();
        setMaze(maze);
    }

    private void spotFood( int food) {
        int x, y;
        Item[][] maze = getMaze();
        int height = maze.length;
        int width = maze[0].length;
        Random random;
        if (seeded)
            random = new Random(food);
        else
            random = new Random();

        for (int i = 0; i < food; i++) {
            do {
                x = random.nextInt(height);
                y = random.nextInt(width);
            } while(! (maze[x][y] instanceof Empty && validFoodLocation(x, y)));
            maze[x][y] = new Item(new Point(x, y), FOOD);
            foodLocs.add(new Point(x, y));
        }
        setMaze(maze);
    }

    private boolean validFoodLocation(int x, int y) {
        Item[][] maze = getMaze();
        for (Point point :
            List.of(new Point(x - 1, y), new Point(x, y + 1), new Point(x + 1, y), new Point(x, y - 1)))
            if ( outOfMaze(point) || maze[point.x()][point.y()].getSymbol() == WALL ) return false;
        return true;
    }

    private Point spotSnake() {
        Random random1;
        int x, y;
        Item[][] maze = getMaze();
        int height = maze.length;
        int width = maze[0].length;

        if (seeded)
            random1 = new Random(NO_OF_SNAKES);
        else
            random1 = new Random();
        x = random1.nextInt(height);
        y = random1.nextInt(width);
        snakeHead = new Point(x, y);
        snake.add(snakeHead);
        maze[x][y] = new Snake(snakeHead, SNAKE);
        return snakeHead;
    }

    void playGame(int food) {
        Item[][] maze = getMaze();
        int height = maze.length;
        int width = maze[0].length;
        Point head = getSnakeHead();
        Queue<Point> snake = getSnake();
        kbd = new Scanner(System.in);
        char dir = ' ';
        while (dir != 'q') {
            gui.replaceGamePanel();
            System.out.println("Enter e(up), s(left), f(right), x(down) or q(uit)");
            if (food == 0) {
                System.out.println("Snake found all of the food.");
                break;
            }
            dir = readChar("esxfq1234");        // input char from console
            if (dir == 'q') break;
            Point newPoint = switch (dir) {
                case 'e', '1' -> new Point(head, new Point(-1, 0));   // up
                case 's', '2' -> new Point(head, new Point(0, -1));   // left
                case 'x', '4' -> new Point(head, new Point(1, 0));    // down
                case 'f', '3' -> new Point(head, new Point(0, 1));    // right
                default -> null;                                                // default
            };
            if (newPoint == null) {
                System.out.println("Try again. Not e, s, x, or f");
                continue;
            }
            if (outOfMaze(newPoint) ) {
                System.out.printf("%s: %d, %d\n", newPoint, height, width);
                System.out.println("Try again.");
                continue;
            }

            Item nextPoint = maze[newPoint.x()][newPoint.y()];
            if (nextPoint.getSymbol() == WALL                           // Collision
             || nextPoint.getSymbol() == SNAKE) {
                System.out.println("Collision");
                continue;
            }

            if ( nextPoint.getSymbol() == EMPTY ) {                     // Next space open?
                snakeTail = snake.remove();
                maze[snakeTail.x()][snakeTail.y()].setSymbol(EMPTY);   // Move snake tail
            }

            if ( nextPoint.getSymbol() == FOOD )  food--;               // Swallow food

            maze[nextPoint.getPoint().x()][nextPoint.getPoint().y()].setSymbol(SNAKE); // Move snake
            head = newPoint;
            snake.add(head);
        }
        setSnakeHead(head);
        setSnake(snake);
        setMaze(maze);
    }

    boolean outOfMaze(Point point) {
        return (point.x() < 0 || point.x() == getMaze().length ||
                point.y() < 0 || point.y() == getMaze()[0].length );
    }

    char readChar(String values) {
        char chr = 0;
        int i = 0;
        do {
            try { chr = ( char ) System.in.read(); }
            catch (IOException e) { continue; }
            i = values.indexOf(chr);
        } while(i == -1);
        return chr;
    }

    private void shortestPathToFood() {}


    Item[][] getMaze() {
        return items;
    }

    public void setMaze(Item[][] maze) {
        items = maze;
    }

    public Point getSnakeHead() {
        return snakeHead;
    }

    public void setSnakeHead( Point snakeHead ) {
        this.snakeHead = snakeHead;
    }

    public Queue<Point> getSnake() {
        return snake;
    }

    public void setSnake( Queue<Point> snake ) {
        this.snake = snake;
    }

    @Override
    public String toString() {
        Item[][] maze = getMaze();
        int width = maze[0].length;
        StringBuilder sb = new StringBuilder();
        String line = '+' + "-".repeat(width) + "+\n";
        sb.append(line);
        for ( Item[] row : maze ) {
            sb.append("|");
            for (Item cell : row) {
                sb.append(cell.symbol);
            }
            sb.append("|").append('\n');
        }
        sb.append(line);
        return sb.toString();
    }

    GameManager(String fn, int food) {             // Game constructor
        snake = new LinkedList<>();

        initMaze(fn);

        spotFood(food);

        setSnakeHead(spotSnake());

        gui = new SnakeGUI(this);
    }

    public static void main(String[] args) {
        String fn = args.length > 0 ? args[0] : "test1.txt";
        new GameManager(fn, NO_OF_FOOD).go(NO_OF_FOOD);
    }
}
