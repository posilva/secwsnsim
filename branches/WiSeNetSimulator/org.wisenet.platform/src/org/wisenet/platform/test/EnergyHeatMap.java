/*
 *     Wireless Sensor Network Simulator
 *   The next generation for WSN Simulations
 */
package org.wisenet.platform.test;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.wisenet.simulator.core.node.Node;

public class EnergyHeatMap extends JPanel {

    public static final int GRADIENT_COLORS = 256;
    /** */
    private static final long serialVersionUID = -2105845119293049049L;
    private static BufferedImage backgroundImage;
    private final BufferedImage dotImage = createFadedCircleImage(96);
    private BufferedImage monochromeImage;
    private BufferedImage heatmapImage;
    private BufferedImage colorImage;
    private LookupTable colorTable;
    private LookupOp colorOp;

    public EnergyHeatMap() {
    }

    public void loadImage(String file) throws IOException {
        backgroundImage = ImageIO.read(new File(file));
        setupImage();


    }

    public void loadImage(BufferedImage img) throws IOException {
        backgroundImage = img;
        setupImage();

    }

    private void paintDot(int x, int y, float v) {
        Point p = new Point(x, y);
        addDotImage(p, v);
        repaint();
    }

    private void setupImage() {
        int width = backgroundImage.getWidth();
        int height = backgroundImage.getHeight();
        colorImage = createEvenlyDistributedGradientImage(new Dimension(GRADIENT_COLORS, 1), Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN.darker(), Color.CYAN, Color.BLUE, new Color(0, 0, 0x33));
        colorTable = createColorLookupTable(colorImage, .5f);
        colorOp = new LookupOp(colorTable, null);
        monochromeImage = createCompatibleTranslucentImage(width, height);
        Graphics g = monochromeImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Creates the color lookup table from an image
     *
     * @param im
     * @return
     */
    public static LookupTable createColorLookupTable(BufferedImage im,
            float alpha) {
        int tableSize = 256;
        Raster imageRaster = im.getData();
        double sampleStep = 1D * im.getWidth() / tableSize; // Sample pixels
        // evenly
        byte[][] colorTable = new byte[4][tableSize];
        int[] pixel = new int[1]; // Sample pixel
        Color c;

        for (int i = 0; i < tableSize; ++i) {
            imageRaster.getDataElements((int) (i * sampleStep), 0, pixel);

            c = new Color(pixel[0]);

            colorTable[0][i] = (byte) c.getRed();
            colorTable[1][i] = (byte) c.getGreen();
            colorTable[2][i] = (byte) c.getBlue();
            colorTable[3][i] = (byte) (alpha * 0xff);
        }

        LookupTable lookupTable = new ByteLookupTable(0, colorTable);

        return lookupTable;
    }

    public static BufferedImage createEvenlyDistributedGradientImage(
            Dimension size, Color... colors) {
        BufferedImage im = createCompatibleTranslucentImage(
                size.width, size.height);
        Graphics2D g = im.createGraphics();

        float[] fractions = new float[colors.length];
        float step = 1f / colors.length;

        for (int i = 0; i < colors.length; ++i) {
            fractions[i] = i * step;
        }

        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0, size.width, 1, fractions, colors,
                MultipleGradientPaint.CycleMethod.REPEAT);

        g.setPaint(gradient);
        g.fillRect(0, 0, size.width, size.height);

        g.dispose();

        return im;
    }

    public static BufferedImage createCompatibleTranslucentImage(int width,
            int height) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        return gc.createCompatibleImage(
                width, height, Transparency.TRANSLUCENT);
    }

    public BufferedImage colorize(LookupOp colorOp) {
        return colorOp.filter(monochromeImage, null);
    }

    public BufferedImage colorize(LookupTable colorTable) {
        return colorize(new LookupOp(colorTable, null));
    }

    public static BufferedImage createFadedCircleImage(int size) {
        BufferedImage im = createCompatibleTranslucentImage(size, size);
        float radius = size / 2f;

        RadialGradientPaint gradient = new RadialGradientPaint(
                radius, radius, radius, new float[]{0f, 1f}, new Color[]{
                    Color.BLACK, new Color(0xffffffff, true)});

        Graphics2D g = (Graphics2D) im.getGraphics();

        g.setPaint(gradient);
        g.fillRect(0, 0, size, size);

        g.dispose();

        return im;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        heatmapImage = colorize(colorOp);

        g.drawImage(backgroundImage, 0, 0, this);
        g.drawImage(heatmapImage, 0, 0, this);
    }

    private void addDotImage(Point p, float alpha) {
        if (alpha > 0.99999f) {
            alpha = 1.0f;
        }
        int circleRadius = dotImage.getWidth() / 2;

        Graphics2D g = (Graphics2D) monochromeImage.getGraphics();

        g.setComposite(BlendComposite.Multiply.derive(alpha));
        g.drawImage(dotImage, null, p.x - circleRadius, p.y - circleRadius);
    }

    public void paintHeat(Collection<Node> nodes) {
        float maxEnergyConsumed = Float.MIN_VALUE;

        for (Node node : nodes) {
            float c = (float) (node.getBateryEnergy().getInitialPower() - node.getBateryEnergy().getCurrentPower());
            maxEnergyConsumed = Math.max(c, maxEnergyConsumed);
        }

        for (Node node : nodes) {
            float c = (float) (node.getBateryEnergy().getInitialPower() - node.getBateryEnergy().getCurrentPower());
            if (maxEnergyConsumed > 0) {
                paintDot((int) node.getX(), (int) node.getY(), c / maxEnergyConsumed);
            }
        }
    }
}
