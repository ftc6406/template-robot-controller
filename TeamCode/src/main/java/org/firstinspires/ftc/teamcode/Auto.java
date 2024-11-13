package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

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
                WEBCAM.addTargetColor(Color.RED);
                break;

            case BLUE_NEAR:
            case BLUE_FAR:
                WEBCAM.addTargetColor(Color.BLUE);
                break;
        }

        /*
         * Hard coded robot movement for autonomous
         */
        if (startPosition == StartPosition.RED_NEAR) {
            WHEELS.drive(20.0, -10.0);
        }

        if (startPosition == StartPosition.RED_FAR) {
            WHEELS.drive(20.0, 10.0);
        }

        if (startPosition == StartPosition.BLUE_NEAR) {
            WHEELS.drive(20.0, 10.0);
        }

        if (startPosition == StartPosition.BLUE_FAR) {
            WHEELS.driveDistance(20.0, -10.0);
        }

        WHEELS.driveDistance(1.0, 2.0);
        WHEELS.turn(90);

        telemetry.addData("Hello world!", "");
        autoSleep(WHEELS.getMotors(), new HashSet<>());

        sleep(60000);

        WEBCAM.getVisionPortal().stopStreaming();
    }
}