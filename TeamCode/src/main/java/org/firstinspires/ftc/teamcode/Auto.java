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

        telemetry.addData("Staring position", startPosition.name());

        while (opModeIsActive()) {
            WHEELS.drive(0.0, 1.0, 0.0);

            telemetry.addData("Hello world!", "");
            autoSleep(WHEELS.getMotors(), new HashSet<>());

            sleep(60000);
        }

        WEBCAM.getVisionPortal().close();
    }
}