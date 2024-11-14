package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.HashSet;
import java.util.List;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {
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