package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.opencv.core.Scalar;

import java.util.HashSet;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {
    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        super.runOpMode();

        telemetry.addData("Starting position", startPosition.name());

        switch (startPosition) {
            case RED_NEAR:
            case RED_FAR:
                WEBCAM.setTargetColorRange(Color.LOW_HUE_RED.getRange());
                break;

            case BLUE_NEAR:
            case BLUE_FAR:
                WEBCAM.setTargetColorRange(Color.BLUE.getRange());
                break;
        }

        while (opModeIsActive()) {
            WHEELS.drive(0.0, 1.0, 0.0);

            telemetry.addData("Hello world!", "");
            autoSleep(WHEELS.getMotors(), new HashSet<>());

            sleep(60000);
        }

        WEBCAM.getVisionPortal().stopStreaming();
    }
}