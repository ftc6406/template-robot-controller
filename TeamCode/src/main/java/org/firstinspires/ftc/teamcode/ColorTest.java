package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardwareSystems.Webcam;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

@TeleOp(name = "ColorTest")
public class ColorTest extends OpMode {
    private Webcam webcam;

    @Override
    public void init() {
        int[] resolution = {640, 480};
        webcam = new Webcam(hardwareMap.get(WebcamName.class, "webcam"), resolution);
    }

    @Override
    public void loop() {
        PredominantColorProcessor.Result colorResult = webcam.getColorResult();
        // Update the information from the robot
        telemetry.addData("Best Match:", colorResult.closestSwatch);

        int red = Color.red(colorResult.rgb);
        int green = Color.green(colorResult.rgb);
        int blue = Color.blue(colorResult.rgb);
        telemetry.addLine("rgb(" + red + ", " + green + ", " + blue + ")");

        telemetry.update();
    }
}