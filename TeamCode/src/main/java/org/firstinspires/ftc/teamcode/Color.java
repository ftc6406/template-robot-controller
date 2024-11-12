package org.firstinspires.ftc.teamcode;

import org.opencv.core.Scalar;

import java.util.HashSet;

public enum Color {
    /**
     * Magenta to reddish-orange
     */
    RED(
            new Scalar[]{new Scalar(0, 65, 65), new Scalar(10, 255, 255)},
            new Scalar[]{new Scalar(170, 65, 65), new Scalar(180, 255, 255)}
    ),

    /**
     * Cyan to indigo
     */
    BLUE(
            new Scalar[]{new Scalar(90, 65, 65), new Scalar(130, 255, 255)}
    );

    /**
     * Yellow-orange to lime-yellow.
     */
    YELLOW(
            new Scalar[]{new Scalar{20, 80, 80}}
    )

    /*
     * Contain the ranges of the colors.
     * Necessary because of one color(i.e. red).
     */
    private HashSet<Scalar[]> ranges;

    Color(Scalar[]... ranges) {
        ranges = ranges;
    }

    public HashSet<Scalar[]> getRanges() {
        return ranges;
    }
}