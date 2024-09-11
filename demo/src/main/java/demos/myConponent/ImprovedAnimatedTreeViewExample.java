package demos.myConponent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.geometry.Insets;

public class ImprovedAnimatedTreeViewExample extends Application {

    private Rectangle selectionRectangle;
    private Timeline fadeInTimeline;
    private double lastY = 0;

    @Override
    public void start(Stage primaryStage) {
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        root.getChildren().addAll(
            new TreeItem<>("Dashboard"),
            new TreeItem<>("Profile"),
            new TreeItem<>("Messages"),
            new TreeItem<>("Settings")
        );

        TreeView<String> treeView = new TreeView<>(root);
        treeView.setShowRoot(false);
        treeView.setPrefWidth(200);

        selectionRectangle = new Rectangle(200, 40);
        selectionRectangle.setArcWidth(10);
        selectionRectangle.setArcHeight(10);
        selectionRectangle.setFill(Color.rgb(66, 135, 245, 0.2));
        selectionRectangle.setStroke(Color.rgb(66, 135, 245, 0.5));
        selectionRectangle.setStrokeWidth(2);
        selectionRectangle.setVisible(false);

        StackPane stackPane = new StackPane(selectionRectangle, treeView);
        stackPane.setPadding(new Insets(10));

        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                animateSelection(treeView.getRow(newSelection));
            }
        });

        Scene scene = new Scene(stackPane, 250, 400);
        //scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        primaryStage.setTitle("Smooth TreeView Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void animateSelection(int index) {
        double newY = index * 40 + 10; // 40 is the height of each cell, 10 is the top padding

        selectionRectangle.setVisible(true);

        // Move animation
        TranslateTransition moveTransition = new TranslateTransition(Duration.millis(200), selectionRectangle);
        moveTransition.setFromY(lastY);
        moveTransition.setToY(newY);

        // Scale animation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), selectionRectangle);
        scaleTransition.setFromX(0.8);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(0.8);
        scaleTransition.setToY(1);

        // Fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), selectionRectangle);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(moveTransition, scaleTransition, fadeTransition);
        parallelTransition.play();

        lastY = newY;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
