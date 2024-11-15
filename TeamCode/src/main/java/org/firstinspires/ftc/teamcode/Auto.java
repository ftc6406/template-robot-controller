package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.hardwareSystems.IntakeClaw;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.HashSet;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {

    private static final double TICKS_PER_DEGREE = 175;

    /**
     * Raises the arm 28.5 inches and ejects the object.
     */
    public void raiseArmANDEject() {
        // Set target degrees to reach the height of 28.5 inches
        double targetDegrees = 90; // Replace with actual degrees needed to reach 28.5 inches

        // Move the arm to the calculated target position
        ARM.rotateArmToPosition(targetDegrees);

        // Eject the object using the claw
        IntakeClaw.ejectIntake();
    }

    public void nearDriveToBucket() {
        int targetTagId = (ALLIANCE_COLOR == AllianceColor.RED) ? 16 : 13;

        WHEELS.turn(-90);

        // Drive in front of the AprilTag next to the bucket.
        List<AprilTagDetection> currentDetections = WEBCAM.getAprilTag().getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetTagId) {
                WHEELS.driveDistance(detection.ftcPose.x, detection.ftcPose.y - 20);
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
            WHEELS.turn(-45);

            // Drop pixel

            // Turn to face yellow pixels
            WHEELS.turn(135);



        } else {
            WHEELS.driveDistance(20);
        }

        if (isStopRequested()) {
            ARM.rotateArmToPosition(0);
        }
    }
}