package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

@TeleOp(name = "DriverMode")
public class DriverMode extends CustomLinearOp {
    @Override
    public void runOpMode() {
        super.runOpMode();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        telemetry.addData("cameraMonitorViewId", cameraMonitorViewId);


        while (opModeIsActive()) {

            telemetry.addData("foldingArm", ARM.getFoldingTicks());
            telemetry.addData("contourPosition", WEBCAM.getContourPosition());
            telemetry.addData("numContours", WEBCAM.getPipeLine().numContours);

            /* Gamepad 1 (Wheel and Webcam Controls) */
            /* Wheel Controls */
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

            // Save CPU resources; can resume streaming when needed.
            if (gamepad1.dpad_down) {
                WEBCAM.getVisionPortal().stopStreaming();
            } else if (gamepad1.dpad_up) {
                WEBCAM.getVisionPortal().resumeStreaming();
            }

            /* Gamepad 2 (Arm and Claw Controls) */

            /*
             * The right joystick on gamepad2 controls both arm rotation and extension:
             * Up rotates the arm up, and down rotates it down.
             * Right extends the arm, left retracts it.
             */
            ARM.rotateArm(gamepad2.right_stick_y);
            ARM.foldArm(gamepad2.left_stick_x);

            /*
             * D-pad controls the claw's X-axis rotation.
             */
            if (gamepad2.dpad_left) {
                CLAW.rotateXAxisServo(-1.0);
            } else if (gamepad2.dpad_right) {
                CLAW.rotateXAxisServo(1.0);
            }

            /*
             * The left bumper rotates the claw counter-clockwise around the Z-axis,
             * and the right bumper rotates it clockwise around the Z-axis.
             */
            if (gamepad2.left_bumper) {
                CLAW.rotateZAxisServo(-1.0);
            } else if (gamepad2.right_bumper) {
                CLAW.rotateZAxisServo(1.0);
            }
            /*
             * Pressing A picks up samples.
             * Pressing B stops the intake.
             * Pressing Y releases the sample.
             */
            if (gamepad2.a) {
                CLAW.startIntake();

            } else if (gamepad2.b) {
                CLAW.stopIntake();

            } else if (gamepad2.y) {
                CLAW.ejectIntake();
            }

            telemetry.update();
        }
    }
}