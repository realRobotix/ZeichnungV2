import java.awt.Color;

public class Zeichnung {
    private final Square square;

    public Zeichnung() {
        this.square = new Square(100, 50, 75, 100, Color.red);
    }

    public void zeichne() {
        this.square.draw();

        while (true) {
            this.square.morph(50, 100, 600, 75, Color.green, 1000, 2);
            this.square.morph(100, 50, 575, 600, Color.blue, 1000, 2);
            this.square.morph(50, 100, 100, 575, Color.green, 5000, 2);
            this.square.morph(100, 50, 75, 100, Color.red, 5000, 2);
        }
    }
}
