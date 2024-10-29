package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

@TeleOp(name = "ColorTest")
public class ColorTest extends OpMode {
    private Hardware hardware;

    @Override
    public void init() {
        hardware = new Hardware(this);
    }

    @Override
    public void loop() {
        PredominantColorProcessor.Result colorResult = hardware.getWebCam().getColorResult();
        // Update the information from the robot
        telemetry.addData("Best Match:", colorResult.closestSwatch);

        int red = Color.red(colorResult.rgb);
        int green = Color.green(colorResult.rgb);
        int blue = Color.blue(colorResult.rgb);
        telemetry.addLine("rgb(" + red + ", " + green + ", " + blue + ")");

        telemetry.update();
    }
}