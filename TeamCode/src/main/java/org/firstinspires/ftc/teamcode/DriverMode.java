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
        /* Gamepad 1 (Wheel and Webcam Controls) */

        /* Wheel Controls */
        /*
         * Drive robot based on joystick input from gamepad1
         * Right stick moves the robot forwards and backwards and turns it.
         * The triggers controls strafing.
         */
        double strafe = (gamepad1.left_trigger > 0) ? -gamepad1.left_trigger : gamepad1.right_trigger;
        WHEELS.drive(
                strafe,
                gamepad1.right_stick_y,
                gamepad1.left_stick_x
        );

        telemetry.addData("Front left wheel power", WHEELS.getFrontLeftMotor().getPower());
        telemetry.addData("Front right wheel power", WHEELS.getFrontRightMotor().getPower());
        telemetry.addData("Back left wheel power", WHEELS.getBackLeftMotor().getPower());
        telemetry.addData("Back right wheel power", WHEELS.getBackRightMotor().getPower());

        /* Webcam controls */
        // Save CPU resources; can resume streaming when needed.
        /*
        if (gamepad1.dpad_down) {
            WEBCAM.getVisionPortal().stopStreaming();
        } else if (gamepad1.dpad_up) {
            WEBCAM.getVisionPortal().resumeStreaming();
        }
         */

        /* Gamepad 2 (Arm and Claw Controls) */

        /*
         * The right joystick on gamepad2 controls the arm rotation and folding.
         */
        ARM.rotate(gamepad2.right_stick_y);
        telemetry.addData("Rotation position", ARM.getRotationTicks() + ", " + ARM.getRotationDegrees() + '°');
        telemetry.addData("Rotation power", ARM.getRotationMotor().getPower());

        ARM.fold(gamepad2.left_stick_x);
        telemetry.addData("Folding position", ARM.getFoldingTicks() + ", " + ARM.getFoldingDegrees() + '°');
        telemetry.addData("Folding power", ARM.getFoldingMotor().getPower());

        /*
         * D-pad controls the claw's X-axis rotation.
         */
        if (gamepad2.dpad_left) {
            CLAW.rotateRollServo(-1.0);

        } else if (gamepad2.dpad_right) {
            CLAW.rotateRollServo(1.0);
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

        // Bumper Controls
        if (gamepad2.right_bumper) {
            telemetry.addLine("Right Bumper Pressed: Raising Arm, Rotating Servo, and Rotating Claw");

            // Raise the arm to -90 degrees (adjust as needed)
            double targetDegrees = 90.0;
            ARM.rotateToAngle(targetDegrees);

            // Rotate the claw to 180 degrees
            CLAW.rotateRollServoToAngle(180);

            telemetry.addData("Arm Target", targetDegrees);
            telemetry.addData("Claw Target", 90.0);

        } else if (gamepad2.left_bumper) {
            telemetry.addLine("Left Bumper Pressed: Lowering Arm, Rotating Servo Back, and Running Intake");

            // Lower the arm to 90 degrees (adjust as needed)
            double setPosition = 90.0;
            ARM.rotateToAngle(setPosition);

            // Rotate the claw to 0 degrees
            CLAW.rotateRollServoToAngle(0);

            // Run the intake servo
            CLAW.ejectIntake();

            telemetry.addData("Arm Target", setPosition);
            telemetry.addLine("Eject intake");
        }

        telemetry.update();
    }

    @Override
    public void runOpMode() {
        super.runOpMode();

        /*
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
        );
        WEBCAM.getVisionPortal().stopStreaming();
         */

        while (opModeIsActive()) {
            try {
                runLoop();

            } catch (Exception e) {
                telemetry.addLine("\nWARNING AN ERROR OCCURRED!!!");
                telemetry.addLine(e.getMessage());
            }
        }
    }
}