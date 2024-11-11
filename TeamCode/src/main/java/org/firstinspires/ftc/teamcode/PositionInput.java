package org.firstinspires.ftc.teamcode;

import java.io.IOException;
import java.nio.file.*;

import com.qualcomm.robotcore.eventloop.opmode.*;

@TeleOp(name = "PositionInput")
public class PositionInput extends OpMode {
    // File name of the storage file,
    // which is inside the directory specified in FileManager.
    private final Path STORAGE_FILE = FileManager.getSeasonDirectory().resolve("position.txt");

    /**
     * Reads the current position stored in the external storage file
     * and prints it.
     * Prints an error message if it fails.
     */
    private void printRobotPosition() {
        try {
            telemetry.addData("Current position: ", FileManager.readFile(STORAGE_FILE));

        } catch (IOException e) {
            telemetry.addLine("ERROR: FAILED TO READ ROBOT POSITION FROM STORAGE FILE!");
        }
    }

    @Override
    public void init() {
        printRobotPosition();
    }

    @Override
    public void loop() {
        telemetry.update();

        telemetry.addData("seasonDir", FileManager.getSeasonDirectory());
        telemetry.addData("STORAGE_FILE", STORAGE_FILE.toString());

        String positionString = null;
        if (gamepad1.y || gamepad2.y) {
            // Orange button
            positionString += StartPosition.RED_NEAR.name();

        } else if (gamepad1.b || gamepad2.b) {
            // Red button
            positionString = StartPosition.RED_FAR.name();

        } else if (gamepad1.a || gamepad2.a) {
            // Green button
            positionString = StartPosition.BLUE_NEAR.name();

        } else if (gamepad1.x || gamepad2.x) {
            // Blue button
            positionString += StartPosition.BLUE_FAR.name();
        }

        // Do nothing if the driver didn't press any buttons.
        if (positionString == null) {
            return;
        }

        // Try to write to file.
        try {
            FileManager.writeToFile(STORAGE_FILE, positionString);

        } catch (IOException e) {
            telemetry.addLine("ERROR: FAILED TO WRITE ROBOT POSITION TO STORAGE FILE!");
            telemetry.addLine(e.toString());
        }

        printRobotPosition();
    }
}