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
         * D-pad controls the claw's X-axis rotation.
         */
        if (gamepad2.dpad_left) {
            CLAW.rotateRollServo(-1.0);

        } else if (gamepad2.dpad_right) {
            CLAW.rotateRollServo(1.0);
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