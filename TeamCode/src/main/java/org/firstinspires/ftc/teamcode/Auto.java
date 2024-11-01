package org.firstinspires.ftc.teamcode;

import android.util.Size;

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

        // teamColor = (hardware.getColorSwitch().getState()) ? TeamColor.RED : TeamColor.BLUE;
        // teamSide = (hardware.getSideSwitch().getState()) ? TeamSide.FAR : TeamSide.NEAR;

        // telemetry.addData("Initial position:", teamColor.name() + ' ' + teamSide.name());

        while (opModeIsActive()) {


            // hardware.getClaw().startIntake();
//          hardware.getWheels().driveDistance(36);
            WHEELS.drive(0.0, 1.0, 0.0);

            telemetry.addData("Hello world!", "");
            autoSleep(WHEELS.getMotors(), new HashSet<>());

            sleep(60000);
        }

        WEBCAM.getVisionPortal().close();
    }
}