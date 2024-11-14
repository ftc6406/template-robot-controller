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
        telemetry.update();
        sleep(15000);

        /*
         * Hard coded robot movement for autonomous
         */
        // Moves the robot to the alliance-specific spike mark
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

        telemetry.addData("Hello world!", "");
        autoSleep(WHEELS.getMotors(), new HashSet<>());

        sleep(30000);
    }
}