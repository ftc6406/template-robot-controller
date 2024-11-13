package org.firstinspires.ftc.teamcode;

import org.opencv.core.Scalar;

/**
 * Specify a range of HSV values for each color.
 */
public enum Color {
    /**
     * Red to reddish-orange.
     */
    RED(new Scalar(0, 70, 50), new Scalar(10, 255, 255)),

    /**
     * Magenta to red.
     */
    MAGENTA(new Scalar(-170, 70, 50), new Scalar(180, 255, 255)),

    /**
     * Teal to indigo.
     */
    BLUE(new Scalar(90, 70, 50), new Scalar(125, 255, 255)),

    /**
     * Yellow-orange to lime-yellow.
     */
    YELLOW(new Scalar(20, 70, 50), new Scalar(33, 255, 255));

    private final Scalar lowerBound;
    private final Scalar upperBound;

    private Color(Scalar lowerBound, Scalar upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Scalar getLowerBound() {
        return lowerBound;
    }

    public Scalar getUpperBound() {
        return upperBound;
    }

    public Scalar[] getRange() {
        return new Scalar[] {
                lowerBound,
                upperBound
        };
    }
}