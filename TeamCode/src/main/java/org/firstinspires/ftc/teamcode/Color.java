package org.firstinspires.ftc.teamcode;

import org.opencv.core.Scalar;

/**
 * Specify a range of HSV values for each color.
 */
public enum Color {
    /**
     * Red to reddish-orange.
     */
    LOW_HUE_RED(new Scalar(0, 65, 65), new Scalar(10, 255, 255)),

    /**
     * Magenta to red.
     */
    HIGH_HUE_RED(new Scalar(170, 65, 65), new Scalar(180, 255, 255)),

    /**
     * Teal to indigo.
     */
    BLUE(new Scalar(100, 65, 65), new Scalar(125, 255, 255)),

    YELLOW(new Scalar(0, 0, 0), new Scalar(0, 0, 0));


    private Scalar lowerBound;
    private Scalar upperBound;

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