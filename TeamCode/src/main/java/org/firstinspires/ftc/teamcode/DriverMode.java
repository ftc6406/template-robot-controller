package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

@TeleOp(name = "DriverMode")
public class DriverMode extends OpMode {
    private Hardware hardware;

    @Override
    public void init() {
        hardware = new Hardware(this);
    }

    @Override
    public void loop() {
        /*
         * Drive robot based on joystick input from gamepad1
         * Right stick moves the robot forwards and backwards and turns it.
         * The triggers controls strafing.
         */
        double strafe = (gamepad1.left_trigger > 0) ? -gamepad1.left_trigger : gamepad1.right_trigger;
        hardware.getWheels().drive(
                gamepad1.right_stick_y,
                strafe,
                gamepad1.left_stick_x
        );

        /*
         * The right joystick on gamepad2 controls both arm rotation and extension:
         * Up rotates the arm up, and down rotates it down.
         * Right extends the arm, left retracts it.
         */
//        hardware.getArm().rotateArm(gamepad2.right_stick_y);
//        hardware.getArm().extendArm(gamepad2.right_stick_x);

        /*
         * The left joystick on gamepad2 controls the claw rotation:
         * Right rotates the claw clockwise on the X-axis, left rotates it counterclockwise.
         * Up rotates the claw clockwise on the Z-axis, left rotates it counterclockwise.
         */
//        hardware.getArm().rotateClawXServo(gamepad2.left_stick_x);

        double zRotate = 0;
        if (gamepad1.left_bumper) {
            zRotate = -1;
        } else if (gamepad1.right_bumper) {
            zRotate = 1;
        }
        hardware.getClaw().rotateZAxisServo(zRotate);

        if (gamepad2.a) {
            hardware.getClaw().startIntake();
        } else if (gamepad2.b) {
            hardware.getClaw().stopIntake();
        } else if (gamepad2.y) {
            hardware.getClaw().ejectIntake();
        }

        // Update the information from the robot
        telemetry.update();
    }
}