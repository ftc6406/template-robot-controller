package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

@TeleOp(name = "DriverMode")
public class DriverMode extends CustomLinearOp {
    @Override
    public void runOpMode() {
        super.runOpMode();

        /* Gamepad 1 (Wheel Controls) */

        /*
         * Drive robot based on joystick input from gamepad1
         * Right stick moves the robot forwards and backwards and turns it.
         * The triggers controls strafing.
         */
        double strafe = (gamepad1.left_trigger > 0) ? -gamepad1.left_trigger : gamepad1.right_trigger;
        WHEELS.drive(
                gamepad1.right_stick_y,
                strafe,
                gamepad1.left_stick_x
        );

        /* Gamepad 2 (Arm and Claw Controls) */

        /*
         * The right joystick on gamepad2 controls both arm rotation and extension:
         * Up rotates the arm up, and down rotates it down.
         * Right extends the arm, left retracts it.
         */
        ARM.rotateArm(gamepad2.right_stick_y);
        ARM.extendArm(gamepad2.left_stick_x);

        /*
         * The left joystick on gamepad2 controls the claw rotation:
         * Right rotates the claw clockwise on the X-axis, left rotates it counterclockwise.
         * Up rotates the claw clockwise on the Z-axis, left rotates it counterclockwise.
         */
        CLAW.rotateXAxisServo(gamepad2.left_stick_x);

        double zRotate = 0;
        if (gamepad1.left_bumper) {
            zRotate = -1;
        } else if (gamepad1.right_bumper) {
            zRotate = 1;
        }
        CLAW.rotateZAxisServo(zRotate);

        if (gamepad2.a) {
            CLAW.startIntake();

        } else if (gamepad2.b) {
            // CLAW.stopIntake();

        } else if (gamepad2.y) {
            CLAW.ejectIntake();
        }


    }
}