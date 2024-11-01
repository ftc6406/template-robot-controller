package org.firstinspires.ftc.teamcode.hardwareSystems;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.opencv.*;
import org.firstinspires.ftc.vision.VisionPortal;

public class Webcam {
    private final VisionPortal VISION_PORTAL;
    private final AprilTagProcessor APRIL_TAG;
    private final PredominantColorProcessor COLOR_PROCESSOR;

    public Webcam(WebcamName webcam, int resolutionWidth, int resolutionHeight) {
        APRIL_TAG = new AprilTagProcessor.Builder().build();

        COLOR_PROCESSOR = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.5, 0.5, 0.5, -0.5))
                .setSwatches(
                        PredominantColorProcessor.Swatch.RED,
                        PredominantColorProcessor.Swatch.BLUE,
                        PredominantColorProcessor.Swatch.YELLOW,
                        PredominantColorProcessor.Swatch.BLACK,
                        PredominantColorProcessor.Swatch.WHITE
                        )
                .build();

        VISION_PORTAL = new VisionPortal.Builder()
                .addProcessor(COLOR_PROCESSOR)
                .addProcessor(APRIL_TAG)
                .setCamera(webcam)
                .setCameraResolution(new Size(resolutionWidth, resolutionHeight))
                .build();
    }

    public AprilTagProcessor getAprilTag() {
        return APRIL_TAG;
    }

    public PredominantColorProcessor getColorProcessor() {
        return COLOR_PROCESSOR;
    }

    public VisionPortal getVisionPortal() {
        return VISION_PORTAL;
    }

    public PredominantColorProcessor.Result getColorResult() {
        return COLOR_PROCESSOR.getAnalysis();
    }
}