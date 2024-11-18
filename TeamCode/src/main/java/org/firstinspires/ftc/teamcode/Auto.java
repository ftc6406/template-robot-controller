package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.HashSet;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {

    private static final double TICKS_PER_DEGREE = 175;

    /**
     * Raise the arm 28.5 inches and ejects the sample.
     */
    public void raiseArmAndEject() {
        // Set target degrees to reach the height of 28.5 inches
        double targetDegrees = 170; // Replace with actual degrees needed to reach 28.5 inches

        // Move the arm to the calculated target position
        ARM.rotateArmToAngle(targetDegrees);

        // Eject the object using the claw
        CLAW.ejectIntake();
    }

    public void pickUpSample() {
        ARM.rotateArmToAngle(0);
        CLAW.startIntake();
        sleep(100);
        CLAW.stopIntake();
    }

    public void nearDriveToBucket() {
        int targetTagId = (ALLIANCE_COLOR == AllianceColor.RED) ? 16 : 13;

        // Drive in front of the AprilTag next to the bucket.
        List<AprilTagDetection> currentDetections = WEBCAM.getAprilTag().getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetTagId) {
                double forwardDistance = detection.ftcPose.y + WEBCAM.getPoseAdjust()[1] - 20;
                double sidewaysDistance = detection.ftcPose.x + WEBCAM.getPoseAdjust()[0];
                WHEELS.driveDistance(sidewaysDistance, forwardDistance);
            }
        }
        autoSleep();
    }

    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        super.runOpMode();
        telemetry.update();

        /*
         * Hard coded robot movement for autonomous
         */
        if (TEAM_SIDE == TeamSide.NEAR) {
            nearDriveToBucket();

            // Turn to face the bucket
            WHEELS.turn(45);
            raiseArmAndEject();

            // Drop pixel

            // Turn to face yellow pixels
            WHEELS.turn(-45);

            // Drive to pixel and pick it up
            double[] contourPosition = WEBCAM.getContourPosition();
            if (contourPosition.length != 0) {
                WHEELS.driveDistance(contourPosition[1]);

            } else {
                WHEELS.driveDistance(12);
            }
            autoSleep(WHEELS.getMotors(), new HashSet<>());
            pickUpSample();

            // Drive back and dump it in bucket
            if (contourPosition.length != 0) {
                WHEELS.driveDistance(-contourPosition[1]);

            } else {
                WHEELS.driveDistance(-12);
            }
            WHEELS.turn(45);
            raiseArmAndEject();

            // Park
            WHEELS.turn(-45);
            WHEELS.driveDistance(18, 12);

        } else {
            // Park
            WHEELS.driveDistance(20);
        }

        // When the Autonomous is stopped, lower the arm to prevent damage.
        if (isStopRequested()) {
            ARM.rotateArmToAngle(0);
        }
    }
}