import java.awt.Color;

public class Zeichnung {
    private final Square square;

    public Zeichnung() {
        this.square = new Square(100, 100, 0, 100, Color.red);
    }

    public void zeichne() {
        Canvas canvas = Canvas.getCanvas();
        this.square.draw();

        for(int i = 0; i < 10; ++i) {
            this.square.morph(50, 200, 200, 70, Color.green, 5000, 10);
            this.square.morph(100, 100, 0, 100, Color.red, 5000, 10);
        }

    }
}
