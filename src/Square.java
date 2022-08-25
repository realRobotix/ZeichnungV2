import java.awt.Color;
import java.awt.Rectangle;

public class Square {
    public int width;
    public int height;
    public int x;
    public int y;
    public Color color;

    public Square(int width, int height, int x, int y, Color color) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void morph(int width, int height, int x, int y, Color color, int milliseconds, int refreshRate) {
        int oldWidth = this.width;
        int oldHeight = this.height;
        int oldX = this.x;
        int oldY = this.y;
        int oldRed = this.color.getRed();
        int oldGreen = this.color.getGreen();
        int oldBlue = this.color.getBlue();
        int oldAlpha = this.color.getAlpha();
        int refreshes = milliseconds / refreshRate;

        for (int i = 0; i <= refreshes; ++i) {
            float percent = (float) i / (float) refreshes;
            this.x = (int) ((float) oldX + (float) (x - oldX) * percent);
            this.y = (int) ((float) oldY + (float) (y - oldY) * percent);
            this.width = (int) ((float) oldWidth + (float) (width - oldWidth) * percent);
            this.height = (int) ((float) oldHeight + (float) (height - oldHeight) * percent);
            this.color = new Color((int) ((float) oldRed + (float) (color.getRed() - oldRed) * percent), (int) ((float) oldGreen + (float) (color.getGreen() - oldGreen) * percent), (int) ((float) oldBlue + (float) (color.getBlue() - oldBlue) * percent), (int) ((float) oldAlpha + (float) (color.getAlpha() - oldAlpha) * percent));
            this.draw();

            try {
                Thread.sleep((long) refreshRate);
            } catch (Exception ignored) {
            }
        }

    }

    public void draw() {
        Canvas canvas = Canvas.getCanvas();
        canvas.zeichne(this, this.color, new Rectangle(this.x, this.y, this.width, this.height));
    }

    public void delete() {
        Canvas canvas = Canvas.getCanvas();
        canvas.remove(this);
    }
}
