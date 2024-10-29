package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.*;

import java.util.HashSet;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {
    private Hardware hardware;

    // Red or blue team
    private TeamColor teamColor;
    // Far or near
    private TeamSide teamSide;

    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        hardware = new Hardware(this);

        // teamColor = (hardware.getColorSwitch().getState()) ? TeamColor.RED : TeamColor.BLUE;
        // teamSide = (hardware.getSideSwitch().getState()) ? TeamSide.FAR : TeamSide.NEAR;

        // telemetry.addData("Initial position:", teamColor.name() + ' ' + teamSide.name());

        // Wait until the player press the start button
        waitForStart();

        while (opModeIsActive()) {


            // hardware.getClaw().startIntake();
//          hardware.getWheels().driveDistance(36);
            hardware.getWheels().drive(0.0, 1.0, 0.0);

            telemetry.addData("Hello world!", "");
            hardware.autoSleep(hardware.getWheels().getMotors(), new HashSet<>());

            sleep(60000);
        }
    }
}