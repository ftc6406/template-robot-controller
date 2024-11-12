package org.firstinspires.ftc.teamcode;

import org.opencv.core.Scalar;

public enum Color {
    RED(new Scalar(100, 50, 50), new Scalar(255, 70, 70));

    private Scalar lowerBound;
    private Scalar upperBound;

    private Color(Scalar lowerBound, Scalar upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}