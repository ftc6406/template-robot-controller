
package org.firstinspires.ftc.teamcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

import android.os.Environment;

public class FileManager {
    /*
     * The directory that saves the current season's storage files.
     */
    private static final Path seasonDirectory = Paths.get(
            Environment.getExternalStorageDirectory().getAbsolutePath(),
            "2024-2025IntoTheDeep"
    );

    public static Path getSeasonDirectory() {
        return seasonDirectory;
    }

    /**
     * Writes a String to a text file inside this season's directory.
     * Creates the file if it does not already exist
     *
     * @param fileName    The name of the file being written to.
     * @param inputString The String that is written to the file
     * @throws IOException If the file could not be written to.
     */
    public static void writeToFile(String fileName, String inputString) throws IOException {
        writeToFile(Paths.get(fileName), inputString);
    }

    /**
     * Writes a String to a text file inside this season's directory.
     * Creates the file if it does not already exist
     *
     * @param filePath     The relative path of the file being written to.
     * @param outputString The String that is written to the file
     * @throws IOException If the file could not be written to.
     */
    public static void writeToFile(Path filePath, String outputString) throws IOException {
        // If this season's directory does not exist, create it.
        if (!Files.exists(seasonDirectory)) {
            Files.createDirectory(seasonDirectory);
        }

        Path writeFile = seasonDirectory.resolve(filePath);
        // Create the file if it does not exist
        Files.createFile(writeFile);

        // Write the string to the file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(writeFile.toFile()))) {
            writer.write(outputString);
            writer.newLine();
        }
    }

    /**
     * Read a text file inside this season's directory.
     *
     * @param fileName The name of the text file to read.
     * @return The String contained within the text file. If an error occurs, null is returned.
     * @throws IOException File could not be read.
     */
    public static String readFile(String fileName) throws IOException {
        return readFile(Paths.get(fileName));
    }

    /**
     * Read a text file inside this season's directory.
     *
     * @param filePath The relative file path of the text file to read.
     * @return The String contained within the text file. If an error occurs, null is returned.
     * @throws IOException File could not be read.
     */
    public static String readFile(Path filePath) throws IOException {
        Path readFile = seasonDirectory.resolve(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(readFile.toFile()))) {
            StringBuilder data = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append('\n');
            }
            return data.toString();
        }
    }
}