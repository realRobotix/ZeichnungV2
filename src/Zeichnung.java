import java.awt.Color;
import java.util.Random;

public class Zeichnung {

    private final Square square;

    public Zeichnung() {
        this.square = new Square(100, 50, 75, 100, randomColor());
    }

    public void zeichne() {
        //noinspection InfiniteLoopStatement
        while (true) {
            square.morph(50, 100, 600, 75, randomColor(), 2500, 2);
            square.morph(100, 50, 575, 600, randomColor(), 2500, 2);
            square.morph(50, 100, 100, 575, randomColor(), 2500, 2);
            square.morph(100, 50, 75, 100, randomColor(), 2500, 2);
        }
    }

    public Color randomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
