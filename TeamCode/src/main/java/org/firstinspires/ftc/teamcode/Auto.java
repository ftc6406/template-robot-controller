package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

import java.util.HashSet;

@Autonomous(name = "Auto")
public class Auto extends CustomLinearOp {
    /**
     * Automatically runs after pressing the "Init" button on the Control Hub
     */
    @Override
    public void runOpMode() {
        super.runOpMode();

        telemetry.addData("Starting position", ALLIANCE_COLOR.name() + ", " + TEAM_SIDE.name());

        // Both alliances need yellow.
        WEBCAM.addTargetColor(Color.YELLOW);
        switch (ALLIANCE_COLOR) {
            case RED:
                // RED only reads 0 <= H <= 10.
                WEBCAM.addTargetColor(Color.RED);
                // Magenta reads 170 <= H <= 180.
                WEBCAM.addTargetColor(Color.MAGENTA);
                break;

            case BLUE:
                WEBCAM.addTargetColor(Color.BLUE);
                break;
        }

        /*
         * Hard coded robot movement for autonomous
         */
        WHEELS.driveDistance(
                20.0,
                /*
                 * 10.0 if red-far or blue-near.
                 * -10.0 otherwise.
                 */
                (ALLIANCE_COLOR == AllianceColor.RED && TEAM_SIDE == TeamSide.FAR
                        || ALLIANCE_COLOR == AllianceColor.BLUE && TEAM_SIDE == TeamSide.FAR)
                        ? 10.0
                        : -10.0
        );

        WHEELS.driveDistance(1.0, 2.0);
        WHEELS.turn(90);

        telemetry.addData("Hello world!", "");
        autoSleep(WHEELS.getMotors(), new HashSet<>());

        sleep(60000);

        WEBCAM.getVisionPortal().stopStreaming();
    }
}