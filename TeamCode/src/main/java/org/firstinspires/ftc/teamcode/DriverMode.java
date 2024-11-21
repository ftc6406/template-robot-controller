package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "DriverMode")
public class DriverMode extends CustomLinearOp {
    private static final double JOYSTICK_SENSITIVITY = 0.90;

    private static int cameraMonitorViewId;

    /**
     * Run the loop once.
     */
    private void runLoop() {
        try {
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
            telemetry.addData("right stick y", gamepad1.right_stick_y);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("left stick x", gamepad1.left_stick_x);

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
             * The right joystick on gamepad2 controls the arm rotation and folding.
             */
            ARM.rotateArm(gamepad2.right_stick_y);
            telemetry.addData("Rotation power", ARM.getRotationMotor().getPower());

            ARM.foldArm(gamepad2.left_stick_x);
            telemetry.addData("Folding power", ARM.getFoldingMotor().getPower());

            /*
             * D-pad controls the claw's X-axis rotation.
             */
            if (gamepad2.dpad_left) {
                CLAW.rotateXAxisServo(-1.0);

            } else if (gamepad2.dpad_right) {
                CLAW.rotateXAxisServo(1.0);

            }

            // Bumper Controls
            // Pressing the right bumper raises the arm and turns the claw
            // Pressing the left bumper lowers the arm to set position
            //  and rotates the servo back and the intake runs inwards
            if (gamepad2.right_bumper) {
                double targetDegrees = -90.0;

                // Move the arm to the calculated target position
                ARM.rotateArmToAngle(targetDegrees);
                // Spin claw 90 degrees
                CLAW.rotateXAxisServoToPosition(90.0);

            }else if(gamepad2.left_bumper) {
                double setPosition = 90.0;
                ARM.rotateArmToAngle(setPosition);
                CLAW.getIntakeServo().setPower(-0.5);
            }

            /*
             * Pressing A picks up samples.
             * Pressing B stops the intake.
             * Pressing Y releases the sample.
             */
            if (gamepad2.a) {
                telemetry.addLine("Start intake");
//                CLAW.startIntake();
                CLAW.getIntakeServo().setPower(1.0);

            } else if (gamepad2.b) {
                telemetry.addLine("Stop intake");
//                CLAW.stopIntake();
                CLAW.getIntakeServo().setPower(0.0);


            } else if (gamepad2.y) {
                telemetry.addLine("Eject");
//                CLAW.ejectIntake();
                CLAW.getIntakeServo().setPower(-0.5);

            }

        } catch (Exception e) {
            telemetry.addLine(e.getMessage());
        }

        telemetry.update();
    }

    @Override
    public void runOpMode() {
        super.runOpMode();

        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        WEBCAM.getVisionPortal().stopStreaming();

        while (opModeIsActive()) {
            runLoop();
            telemetry.update();
        }
    }
}