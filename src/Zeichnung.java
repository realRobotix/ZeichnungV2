import java.awt.Color;
import java.util.Random;

public class Zeichnung {

    private final Square square;

    public Zeichnung() {
        this.square = new Square(100, 50, 75, 100, Color.red);
    }

    public void zeichne() {
        while (true) {
            square.morph(50, 100, 600, 75, getRandomColor(), 2500, 2);
            square.morph(100, 50, 575, 600, getRandomColor(), 2500, 2);
            square.morph(50, 100, 100, 575, getRandomColor(), 2500, 2);
            square.morph(100, 50, 75, 100, getRandomColor(), 2500, 2);
        }
    }

    public Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
