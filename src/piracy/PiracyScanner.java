package piracy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class PiracyScanner extends Application {

    private static final String[] KEYWORDS = {
        "keygen", "crack", "patch", "loader", "serial", "activation", "license", "pirate", "kg", "key", "nfo"
    };

    private static final String[] EXTENSIONS = {
        ".exe", ".dll", ".nfo", ".zip", ".rar", ".bat", ".dmg", ".apk"
    };

    private static boolean dryRun = false;

    public static void setDryRun(boolean value) {
        dryRun = value;
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a TextArea to display logs
        TextArea logger = new TextArea();
        logger.setEditable(false);

        // Set up the main layout
        StackPane root = new StackPane();
        root.getChildren().add(logger);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Piracy Scanner");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Perform the scan operation (example path for scanning)
        Path path = Paths.get("path/to/scan"); // Adjust this path as needed
        List<File> deletedFiles = scanAndDeletePiracyFiles(path, logger);
    }

    public static List<File> scanAndDeletePiracyFiles(Path path, TextArea logger) {
        List<File> deletedFiles = new ArrayList<>();

        try {
            Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    String fileName = file.getFileName().toString().toLowerCase();
                    boolean isSuspicious = false;

                    // Check for suspicious keywords in the file name
                    for (String keyword : KEYWORDS) {
                        if (fileName.contains(keyword)) {
                            isSuspicious = true;
                            logger.appendText("🔹 Suspicious keyword found: '" + keyword + "' in file: " + fileName + "\n");
                            break;
                        }
                    }

                    // Check for specific extensions and "loader" term in file name
                    for (String ext : EXTENSIONS) {
                        if (fileName.endsWith(ext) && fileName.contains("loader")) {
                            isSuspicious = true;
                            logger.appendText("🔹 Suspicious loader file: " + fileName + "\n");
                            break;
                        }
                    }

                    // If the file is suspicious, either delete it or log based on dry run
                    if (isSuspicious) {
                        if (dryRun) {
                            logger.appendText("👀 [Dry Run] Detected (would delete): " + file.toAbsolutePath() + "\n");
                        } else {
                            try {
                                Files.delete(file);
                                deletedFiles.add(file.toFile());
                                logger.appendText("🗑️ Deleted: " + file.toAbsolutePath() + "\n");
                            } catch (IOException e) {
                                logger.appendText("❌ Failed to delete: " + file.toAbsolutePath() + "\n");
                            }
                        }
                    }
                });
        } catch (IOException e) {
            logger.appendText("❌ Error scanning folder: " + e.getMessage() + "\n");
        }

        return deletedFiles;
    }
}
