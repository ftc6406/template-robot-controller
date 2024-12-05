package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.HashSet;

@Autonomous(name = "TurnTest")
public class TurnTest extends CustomLinearOp {
    @Override
    public void runOpMode() {
        super.runOpMode();

        WHEELS.turn(90);
        telemetry.addData("FL wheel", WHEELS.getFrontLeftMotor().getPower());
        telemetry.addData("FR wheel", WHEELS.getFrontRightMotor().getPower());
        telemetry.addData("BL wheel", WHEELS.getBackLeftMotor().getPower());
        telemetry.addData("BR wheel", WHEELS.getBackRightMotor().getPower());
        telemetry.update();
        autoSleep();
    }
}