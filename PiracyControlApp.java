package piracy;

import piracy.PiracyScanner; // Import statements
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class PiracyControlApp extends Application {

    private TextArea logArea = new TextArea();
    private TextField pathField = new TextField();
    private Button scanBtn = new Button("🚀 Start Scan");
    private Button browseBtn = new Button("📂 Browse");
    private Button saveLogBtn = new Button("💾 Save Log");
    private ProgressBar progressBar = new ProgressBar(0);
    private Label statusLabel = new Label("Status: Ready");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Piracy Control System");

        // Load icon
        try {
            Image icon = new Image(getClass().getResource("/resources/icon.png").toExternalForm());
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("⚠️ Icon not found!");
        }

        // Title
        Label title = new Label("💻 Piracy Control System");
        title.setFont(Font.font("Segoe UI", 36));
        title.setStyle("-fx-text-fill: white;");
        applyFadeIn(title, 1000);

        // Path Input
        pathField.setPromptText("Enter directory path...");
        pathField.setPrefWidth(400);
        pathField.setStyle("-fx-background-color: #2a2d3a; -fx-text-fill: white; -fx-prompt-text-fill: gray;");

        // Button Styling
        styleButton(browseBtn, "#00ADB5");
        styleButton(scanBtn, "#FF5722");
        styleButton(saveLogBtn, "#607D8B");

        // Button Actions
        browseBtn.setOnAction(e -> browseDirectory(primaryStage));
        scanBtn.setOnAction(e -> {
            pulseButton(scanBtn);
            scanFolder();
        });
        saveLogBtn.setOnAction(e -> saveLog(primaryStage));

        // Controls Layout
        HBox controls = new HBox(10, pathField, browseBtn, saveLogBtn);
        controls.setAlignment(Pos.CENTER);

        VBox topLayout = new VBox(10, title, controls, scanBtn);
        topLayout.setAlignment(Pos.CENTER);

        // Logs
        Label logLabel = new Label("📜 Detailed Logs");
        logLabel.setFont(Font.font("Segoe UI", 16));
        logLabel.setStyle("-fx-text-fill: white;");

        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setStyle("-fx-control-inner-background: #1f1f2e; -fx-text-fill: white; -fx-font-family: Consolas;");

        // Status and Progress
        VBox statusLayout = new VBox(10, statusLabel, progressBar);
        statusLayout.setAlignment(Pos.CENTER);
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Main Layout
        VBox layout = new VBox(20, topLayout, logLabel, logArea, statusLayout);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #232526, #414345);");

        Scene scene = new Scene(layout, 820, 620);
        primaryStage.setScene(scene);
        primaryStage.show();

        applyFadeIn(layout, 1500);
    }

    private void styleButton(Button btn, String color) {
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btn.setFont(Font.font("Segoe UI", 14));
        DropShadow shadow = new DropShadow(10, Color.web(color).darker());
        btn.setEffect(null);

        btn.setOnMouseEntered(e -> {
            btn.setScaleX(1.07);
            btn.setScaleY(1.07);
            btn.setEffect(shadow);
        });

        btn.setOnMouseExited(e -> {
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
            btn.setEffect(null);
        });
    }

    private void applyFadeIn(Region node, int durationMs) {
        FadeTransition fade = new FadeTransition(Duration.millis(durationMs), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void pulseButton(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setByX(0.1);
        st.setByY(0.1);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    private void browseDirectory(Stage primaryStage) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Choose Folder to Scan");
        File selectedDir = dirChooser.showDialog(primaryStage);
        if (selectedDir != null) pathField.setText(selectedDir.getAbsolutePath());
    }

    private void scanFolder() {
        File dir = new File(pathField.getText());
        if (!dir.exists() || !dir.isDirectory()) {
            updateLog("❌ Invalid directory path.");
            return;
        }

        updateLog("🔍 Scanning: " + dir.getAbsolutePath());
        statusLabel.setText("Status: Scanning...");

        // You can replace this with your actual scanning logic
        List<File> deletedFiles = PiracyScanner.scanAndDeletePiracyFiles(dir.toPath(), logArea);

        updateLog("✅ Done. Pirated files deleted: " + deletedFiles.size());
        statusLabel.setText("Status: Done ✅");
    }

    private void saveLog(Stage primaryStage) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Log");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                PrintWriter writer = new PrintWriter(file);
                writer.print(logArea.getText());
                writer.close();
                updateLog("✅ Log saved to: " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            updateLog("❌ Failed to save log: " + ex.getMessage());
        }
    }

    private void updateLog(String message) {
        logArea.appendText(message + "\n");
        logArea.setScrollTop(Double.MAX_VALUE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
