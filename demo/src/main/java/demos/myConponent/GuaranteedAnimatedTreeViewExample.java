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

public class GuaranteedAnimatedTreeViewExample extends Application {

    private Rectangle selectionRectangle;
    private TreeView<String> treeView;
    private Timeline animation;

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

        treeView = new TreeView<>(root);
        treeView.setShowRoot(false);
        treeView.setPrefWidth(200);

        selectionRectangle = new Rectangle(200, 40);
        selectionRectangle.setArcWidth(10);
        selectionRectangle.setArcHeight(10);
        selectionRectangle.setFill(Color.rgb(66, 135, 245, 0.2));
        selectionRectangle.setStroke(Color.rgb(66, 135, 245, 0.5));
        selectionRectangle.setStrokeWidth(2);
        selectionRectangle.setTranslateY(-40);  // Start off-screen

        StackPane stackPane = new StackPane(selectionRectangle, treeView);
        stackPane.setPadding(new Insets(10));

        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                animateSelection(treeView.getRow(newSelection));
            }
        });

        Scene scene = new Scene(stackPane, 250, 400);

        primaryStage.setTitle("Guaranteed Smooth TreeView Selection");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Select the first item to show initial animation
        treeView.getSelectionModel().select(0);
    }

    private void animateSelection(int index) {
        double startY = selectionRectangle.getTranslateY();
        double endY = index * 40;  // 40 is the height of each cell

        if (animation != null) {
            animation.stop();
        }

        animation = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(selectionRectangle.translateYProperty(), startY),
                new KeyValue(selectionRectangle.opacityProperty(), 0.5)
            ),
            new KeyFrame(Duration.millis(300),
                new KeyValue(selectionRectangle.translateYProperty(), endY),
                new KeyValue(selectionRectangle.opacityProperty(), 1.0)
            )
        );

        animation.setOnFinished(e -> {
            // Ensure the rectangle is at the correct final position
            selectionRectangle.setTranslateY(endY);
        });

        animation.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
