package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

import com.qualcomm.robotcore.eventloop.opmode.*;

@TeleOp(name = "PositionInput")
public class PositionInput extends OpMode {
    /*
     * The directory that saves the current season's storage files.
     */
    private static final Path SEASON_DIRECTORY = Paths.get(
            Environment.getExternalStorageDirectory().getAbsolutePath(),
            "IntoTheDeep"
    );

    /**
     * File name of the storage file,
     * which is inside the directory specified by `SEASON_DIRECTORY`
     */
    private static final Path STORAGE_FILE = SEASON_DIRECTORY.resolve("position.txt");

    public static Path getSeasonDirectory() {
        return SEASON_DIRECTORY;
    }

    public static Path getStorageFile() {
        return STORAGE_FILE;
    }

    /**
     * Reads the current position stored in the external storage file and prints it.
     * Prints an error message if it fails.
     */
    private void printRobotPosition() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STORAGE_FILE.toFile()))) {
            // Read first line.
            String data = reader.readLine();
            telemetry.addData("Current position: ", data);

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

        telemetry.addData("seasonDir", SEASON_DIRECTORY.toString());
        telemetry.addData("STORAGE_FILE", STORAGE_FILE.toString());

        String positionString = null;
        if (gamepad1.y || gamepad2.y) {
            // Orange button
            positionString += AllianceColor.RED.name() + "," + TeamSide.NEAR.name();

        } else if (gamepad1.b || gamepad2.b) {
            // Red button
            positionString = AllianceColor.RED.name() + "," + TeamSide.FAR.name();

        } else if (gamepad1.a || gamepad2.a) {
            // Green button
            positionString = AllianceColor.BLUE.name() + "," + TeamSide.NEAR.name();

        } else if (gamepad1.x || gamepad2.x) {
            // Blue button
            positionString += AllianceColor.BLUE.name() + "," + TeamSide.FAR.name();
        }

        // Do nothing if the driver didn't press any buttons.
        if (positionString == null) {
            return;
        }

        // Write the string to the file.
        try (FileWriter writer = new FileWriter(STORAGE_FILE.toFile(), false)) {
            // Create the file if it does not exist
            Files.createFile(STORAGE_FILE);
            writer.write(positionString);

        } catch (IOException e) {
            telemetry.addLine("ERROR: FAILED TO WRITE ROBOT POSITION TO STORAGE FILE!");
            telemetry.addLine(e.toString());
        }

        printRobotPosition();
    }
}