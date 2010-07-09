package org.mei.securesim.core.network;

/**
 *
 * @author CIAdmin
 */
public class NetworkField {

    double width;
    double height;
    double level;

    public NetworkField(double width, double height, double level) {
        this.width = width;
        this.height = height;
        this.level = level;
    }

    public NetworkField() {
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
