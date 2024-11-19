package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DriverMode")
public class DriverMode extends CustomLinearOp {
    @Override
    public void runOpMode() {
        super.runOpMode();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );

        while (opModeIsActive()) {
            telemetry.addData("folding ticks", ARM.getFoldingTicks());
            telemetry.addData("cameraMonitorViewId", cameraMonitorViewId);
            telemetry.addData("contourPosition", WEBCAM.getContourPosition());
            telemetry.addData("numContours", WEBCAM.getPipeLine().getNumContours());

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
            telemetry.addData("frontLeftWheel", WHEELS.FRONT_LEFT_MOTOR.getPower());
            telemetry.addData("frontRightWheel", WHEELS.FRONT_RIGHT_MOTOR.getPower());
            telemetry.addData("backLeftWheel", WHEELS.BACK_LEFT_MOTOR.getPower());
            telemetry.addData("backRightWheel", WHEELS.BACK_RIGHT_MOTOR.getPower());

            /* Webcam controls */
            // Save CPU resources; can resume streaming when needed.
            if (gamepad1.dpad_down) {
                WEBCAM.getVisionPortal().stopStreaming();
            } else if (gamepad1.dpad_up) {
                WEBCAM.getVisionPortal().resumeStreaming();
            }

            /* Gamepad 2 (Arm and Claw Controls) */

            /*
             * The right joystick on gamepad2 controls the arm rotation.
             * Up rotates the arm up, and down rotates it down.
             * The left joystick on gamepad1 controls the arm folding.
             * Left rotates it leftward, right rotates it rightward.
             */
            ARM.rotateArm(gamepad2.right_stick_y);
            telemetry.addData("Rotation power", ARM.getRotationMotor().getPower());
            try {
                ARM.foldArm(gamepad2.left_stick_x);
                telemetry.addData("Folding power", ARM.getFoldingMotor().getPower());

            } catch (Exception e) {
                telemetry.addData("Folding motor", e.getMessage());
            }

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
                telemetry.addLine("Start intake");
                CLAW.startIntake();

            } else if (gamepad2.b) {
                telemetry.addLine("Stop intake");
                CLAW.stopIntake();

            } else if (gamepad2.y) {
                telemetry.addLine("Eject");
                CLAW.ejectIntake();
            }

            telemetry.update();
        }
    }
}