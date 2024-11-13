/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.hardwareSystems;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Color;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.opencv.*;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Webcam {
    private static class PipeLine extends OpenCvPipeline {
        private HashSet<Color> targetColors = new HashSet<>();

        private double[] contourPosition;

        @Override
        public Mat processFrame(Mat input) {
            // Convert to HSV color space for easier color detection
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            // Define range of the color you want to detect
            // Scalar lowerBound = new Scalar(50, 100, 100); // Example for green
            // Scalar upperBound = new Scalar(70, 255, 255);

            // Create mask to filter out the desired color(s)
            Mat mask = new Mat();
            Mat tempMask = new Mat();
            // Add each desired color
            for (Color color : targetColors) {
                tempMask = new Mat();
                Core.inRange(hsv, color.getLowerBound(), color.getUpperBound(), tempMask);
                Core.bitwise_or(tempMask, mask, mask);
            }

            // Find contours
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            // Draw contours on the original image
            for (MatOfPoint contour : contours) {
                Rect rect = Imgproc.boundingRect(contour);
                Imgproc.rectangle(input, rect, new Scalar(0, 255, 0), 2); // Green rectangles around objects
            }

            if (!contours.isEmpty()) {
                contourPosition = getContourPosition(contours.get(0).toArray());
            }

            // Release all the memory used for the masks.
            hsv.release();
            mask.release();
            tempMask.release();
            hierarchy.release();
            return input;
        }

        /**
         * Find the middle point of a contour.
         *
         * @param contourPoints An array of the points in the contour.
         * @return Returns the point in the middle of the contour as a double[] of format [x, y].
         */
        private double[] getContourPosition(Point[] contourPoints) {
            double[] position = new double[2];

            double xMax = contourPoints[0].x;
            double xMin = contourPoints[0].x;

            double yMax = contourPoints[0].y;
            double yMin = contourPoints[0].y;

            for (Point p : contourPoints) {
                xMax = Math.max(xMax, p.x);
                xMin = Math.min(xMin, p.x);

                yMax = Math.max(yMax, p.y);
                yMin = Math.min(yMin, p.y);
            }

            position[0] = (xMax + xMin) / 2.0;
            position[1] = (yMax + yMin) / 2.0;

            return position;
        }
    }

    private final VisionPortal VISION_PORTAL;

    private final AprilTagProcessor APRIL_TAG;
    private final PredominantColorProcessor COLOR_PROCESSOR;

    private final OpenCvCamera OPEN_CV_CAMERA;
    private PipeLine pipeLine;

    public Webcam(WebcamName webcam, int[] resolution) {
        this(webcam, resolution, -1);
    }

    public Webcam(WebcamName webcam, int[] resolution, int cameraMonitorId) {
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
                .setCameraResolution(new Size(resolution[0], resolution[1]))
                .build();

        OPEN_CV_CAMERA = (cameraMonitorId != -1)
                ? OpenCvCameraFactory.getInstance().createWebcam(webcam, cameraMonitorId)
                : null;

        // If the OpenCV camera is not used, terminate.
        if (OPEN_CV_CAMERA == null) {
            return;
        }

        // Set the custom pipeline
        pipeLine = new PipeLine();
        OPEN_CV_CAMERA.setPipeline(pipeLine);

        // Open the camera device and start streaming
        OPEN_CV_CAMERA.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                OPEN_CV_CAMERA.startStreaming(resolution[0], resolution[1], OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    public VisionPortal getVisionPortal() {
        return VISION_PORTAL;
    }

    public AprilTagProcessor getAprilTag() {
        return APRIL_TAG;
    }

    public PredominantColorProcessor getColorProcessor() {
        return COLOR_PROCESSOR;
    }

    public PredominantColorProcessor.Result getColorResult() {
        return COLOR_PROCESSOR.getAnalysis();
    }

    public HashSet<Color> getTargetColors() {
        return pipeLine.targetColors;
    }

    public void addTargetColor(Color color) {
        pipeLine.targetColors.add(color);
    }

    public void clearTargetColors() {
        pipeLine.targetColors.clear();
    }

    /**
     * Get the last seen contour position. If the camera has never spotted a contour position, it will return null.
     *
     * @return The last seen contour position.
     */
    public double[] getContourPosition() {
        return pipeLine.contourPosition;
    }
}