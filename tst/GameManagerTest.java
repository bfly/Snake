import org.junit.jupiter.api.Test;

class GameManagerTest {
    @Test
    void testGM1() {
        GameManager gm = new GameManager("maze-cross.txt", 10);
        System.out.println(gm);
    }

    @Test
    void testGM2() {
        GameManager gm = new GameManager("maze-zigzag.txt", 20);
        System.out.println(gm);
    }

    @Test
    void testGM3() {
        GameManager gm = new GameManager("maze-simple.txt", 10);
        System.out.println(gm);
    }
}