package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.HashSet;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {
    /**
     * Raise the arm 28.5 inches and ejects the sample.
     */
    public void raiseArmAndEject() {
        // Set target degrees to reach the height of 28.5 inches
        double targetDegrees = 170.0; // Replace with actual degrees needed to reach 28.5 inches

        // Move the arm to the calculated target position
        ARM.rotateArmToAngle(targetDegrees);
        autoSleep(ARM.getRotationMotor());

        // Eject the object using the claw
        try {
            CLAW.ejectIntake();

        } catch (IllegalStateException e) {
            telemetry.addLine("Failed to eject intake");
        }
    }

    public void pickUpSample() {
        ARM.rotateArmToAngle(0);
        CLAW.startIntake();
        sleep(100);
        CLAW.stopIntake();
    }

    /**
     * When on the near side(i.e. next to bucket),
     * drive to the bucket.
     */
    public void nearDriveToBucket() {
        int targetTagId = (ALLIANCE_COLOR == AllianceColor.RED) ? 16 : 13;

        // Drive in front of the AprilTag next to the bucket.
        List<AprilTagDetection> currentDetections = WEBCAM.getAprilTag().getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetTagId) {
                double forwardDistance = detection.ftcPose.y + WEBCAM.getPoseAdjust()[1] - 12;
                double sidewaysDistance = detection.ftcPose.x + WEBCAM.getPoseAdjust()[0] - 12;
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

        ARM.rotateArmToAngle(0);
        sleep(1000);

        // Actual hard coded movement for robot autonomous
        if (TEAM_SIDE == TeamSide.FAR) {
            ARM.rotateArmToAngle(45);
            sleep(500);
            ARM.foldArmToAngle(75);
            // Temp movement for strafe right
            WHEELS.driveDistance(0, 24);
        }
        /*
         * (Theoretical) hard coded robot movement for near autonomous
         */
        else {
            // Turn to face the bucket
            WHEELS.turn(45);
            // Drop pixel
            raiseArmAndEject();

            // Turn to face yellow pixels
            WHEELS.turn(-45);

            // Drive to right pixel and pick it up
            double[] contourPosition = WEBCAM.getContourPosition();
            if (contourPosition.length != 0) {
                WHEELS.driveDistance(contourPosition[1]);

            } else {
                WHEELS.driveDistance(36, 12);
            }
            autoSleep(WHEELS.getMotors());
            pickUpSample();

            // Drive back and dump it in bucket
            if (contourPosition.length != 0) {
                WHEELS.driveDistance(-contourPosition[1]);

            } else {
                WHEELS.driveDistance(-36, 12);
            }
            WHEELS.turn(45);
            raiseArmAndEject();

            // Park
            ARM.rotateArmToAngle(45);
            WHEELS.turn(-45);
            autoSleep();
            WHEELS.driveDistance(18, 12);

            double targetDegrees = 90.0;

            // Move the arm to the calculated target position
            ARM.rotateArmToAngle(targetDegrees);
            autoSleep(ARM.getRotationMotor());
        }
    }
}