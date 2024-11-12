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
                WEBCAM.setTargetColorRange();
                break;

            case BLUE_NEAR:
            case BLUE_FAR:
                break;
        }

        /**
         * Hard coded robot movement for automonous
         */

        if (startPosition == startPosition.RED_NEAR) {
            WHEELS.drive(20.0, -10.0);
        }

        if (startPosition == startPostion.RED_FAR) {
            WHEELS.drive(20.0, 10.0);
        }

        if (startPostion == startPosition.BLUE_NEAR) {
            WHEELS.drive(20.0, 10.0);
        }

        if (startPostion == startPosition.BLUE_FAR) {
            WHEELS.drive2(20.0, -10.0);
        }

        WHEELS.driveDistance(1.0, 2.0);
        WHEELS.turn(90);

        telemetry.addData("Hello world!", "");
        autoSleep(WHEELS.getMotors(), new HashSet<>());

        sleep(60000);

        WEBCAM.getVisionPortal().close();
    }
}