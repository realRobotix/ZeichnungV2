import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

/**
 * Leinwand ist eine Klasse, die einfache Zeichenoperationen auf einer
 * leinwandartigen Zeichenfl�che erm�glicht. Sie ist eine vereinfachte Version
 * der Klasse Canvas (englisch f�r Leinwand) des JDK und wurde speziell f�r das
 * Projekt "Figuren" geschrieben.
 *
 * @author: Bruce Quig
 * @author: Michael K�lling (mik)
 * @author: Axel Schmolitzky
 *
 * @version: 2006.03.30
 */
public class Canvas {
    // Hinweis: Die Implementierung dieser Klasse (insbesondere die
    // Verwaltung der Farben und Identit�ten der Figuren) ist etwas
    // komplizierter als notwendig. Dies ist absichtlich so, weil damit
    // die Schnittstellen und Exemplarvariablen der Figuren-Klassen
    // f�r den Lernanspruch dieses Projekts einfacher und klarer
    // sein k�nnen.

    private static Canvas canvasSingleton;

    /**
     * Fabrikmethode, die eine Referenz auf das einzige Exemplar dieser Klasse
     * zur�ckliefert. Wenn es von einer Klasse nur genau ein Exemplar gibt, wird
     * dieses als 'Singleton' bezeichnet.
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Figuren Demo", 700, 700,
                    Color.white);
            canvasSingleton.setzeSichtbarkeit(true);
        }
        return canvasSingleton;
    }

    // ----- Exemplarvariablen -----

    private JFrame fenster;

    public Zeichenflaeche zeichenflaeche;

    public Graphics2D graphic;

    private Color hintergrundfarbe;

    private Image leinwandImage;

    private List<Object> figuren;

    private Map<Object, ShapeMitFarbe> figurZuShape;

    /**
     * Erzeuge eine Leinwand.
     *
     * @param titel
     *            Titel, der im Rahmen der Leinwand angezeigt wird
     * @param breite
     *            die gew�nschte Breite der Leinwand
     * @param hoehe
     *            die gew�nschte H�he der Leinwand
     * @param grundfarbe
     *            die Hintergrundfarbe der Leinwand
     */
    private Canvas(String titel, int breite, int hoehe, Color grundfarbe) {
        fenster = new JFrame();
        fenster.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        zeichenflaeche = new Zeichenflaeche();
        fenster.setContentPane(zeichenflaeche);
        fenster.setTitle(titel);
        zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
        hintergrundfarbe = grundfarbe;
        fenster.pack();
        figuren = new ArrayList<Object>();
        figurZuShape = new HashMap<Object, ShapeMitFarbe>();
    }

    /**
     * Setze, ob diese Leinwand sichtbar sein soll oder nicht. Wenn die Leinwand
     * sichtbar gemacht wird, wird ihr Fenster in den Vordergrund geholt. Diese
     * Operation kann auch benutzt werden, um ein bereits sichtbares
     * Leinwandfenster in den Vordergrund (vor andere Fenster) zu holen.
     *
     * @param sichtbar
     *            boolean f�r die gew�nschte Sichtbarkeit: true f�r sichtbar,
     *            false f�r nicht sichtbar.
     */
    public void setzeSichtbarkeit(boolean sichtbar) {
        if (graphic == null) {
            // erstmaliger Aufruf: erzeuge das Bildschirm-Image und f�lle
            // es mit der Hintergrundfarbe
            Dimension size = zeichenflaeche.getSize();
            leinwandImage = zeichenflaeche.createImage(size.width, size.height);
            graphic = (Graphics2D) leinwandImage.getGraphics();
            graphic.setColor(hintergrundfarbe);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        fenster.setVisible(sichtbar);
    }

    /**
     * Zeichne f�r das gegebene Figur-Objekt eine Java-Figur (einen Shape) auf
     * die Leinwand.
     *
     * @param figur
     *            das Figur-Objekt, f�r das ein Shape gezeichnet werden soll
     * @param color
     *            die Farbe der Figur
     * @param shape
     *            ein Objekt der Klasse Shape, das tatsächlich gezeichnet wird
     */
    public void zeichne(Object figur, Color color, Shape shape) {
        figuren.remove(figur); // entfernen, falls schon eingetragen
        figuren.add(figur); // am Ende hinzufügen
        figurZuShape.put(figur, new ShapeMitFarbe(shape, color));
        draw();
    }

    /**
     * Entferne die gegebene Figur von der Leinwand.
     *
     * @param figur
     *            die Figur, deren Shape entfernt werden soll
     */
    public void remove(Object figur) {
        figuren.remove(figur); // entfernen,falls schon eingetragen
        figurZuShape.remove(figur);
        draw();
    }

    /**
     * Warte f�r die angegebenen Millisekunden. Mit dieser Operation wird eine
     * Verz�gerung definiert, die f�r animierte Zeichnungen benutzt werden kann.
     *
     * @param millisekunden
     *            die zu wartenden Millisekunden
     */
    public void wait(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            // Exception ignorieren
        }
    }

    /**
     * Zeichne erneut alle Figuren auf der Leinwand.
     */
    private void draw() {
        clear();
        for (Object figur : figuren) {
            figurZuShape.get(figur).draw(graphic);
        }
        zeichenflaeche.repaint();
    }

    /**
     * L�sche die gesamte Leinwand.
     */
    private void clear() {
        Color color = graphic.getColor();
        graphic.setColor(hintergrundfarbe);
        Dimension size = zeichenflaeche.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(color);
    }

    /***************************************************************************
     * Interne Klasse Zeichenflaeche - die Klasse f�r die GUI-Komponente, die
     * tats�chlich im Leinwand-Fenster angezeigt wird. Diese Klasse definiert
     * ein JPanel mit der zus�tzlichen M�glichkeit, das auf ihm gezeichnet Image
     * aufzufrischen (erneut zu zeichnen).
     */
    private class Zeichenflaeche extends JPanel {

        private static final long serialVersionUID = 20060330L;

        public void paint(Graphics g) {
            g.drawImage(leinwandImage, 0, 0, null);
        }
    }

    /***************************************************************************
     * Interne Klasse ShapeMitFarbe - Da die Klasse Shape des JDK nicht auch
     * eine Farbe mitverwalten kann, muss mit dieser Klasse die Verkn�pfung
     * modelliert werden.
     */
    private class ShapeMitFarbe {
        private Shape shape;

        private Color color;

        public ShapeMitFarbe(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public void draw(Graphics2D graphic) {
            graphic.setColor(color);
            graphic.fill(shape);
        }
    }

}
