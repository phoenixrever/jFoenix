package demos.myConponent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class CorrectAnimatedTreeViewExample extends Application {

    private Rectangle selectionIndicator;

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

        // 创建选择指示器
        selectionIndicator = new Rectangle(4, 30);
        selectionIndicator.setFill(Color.web("#4287f5"));
        selectionIndicator.setTranslateX(-4); // 开始时在视图外

        StackPane stackPane = new StackPane(treeView, selectionIndicator);
        StackPane.setAlignment(selectionIndicator, javafx.geometry.Pos.CENTER_LEFT);

        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                animateSelection(treeView.getRow(newSelection));
            }
        });

        Scene scene = new Scene(stackPane, 250, 400);
        //scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        primaryStage.setTitle("Correct Animated TreeView Selection");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 初始选择
        treeView.getSelectionModel().select(0);
    }

    private void animateSelection(int index) {
        double newY = index * 30; // 假设每个单元格高度为30

        // 创建Y轴移动动画
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), selectionIndicator);
        translateTransition.setToY(newY);

        // 创建X轴移动动画（滑入效果）
        TranslateTransition slideInTransition = new TranslateTransition(Duration.millis(100), selectionIndicator);
        slideInTransition.setFromX(-4);
        slideInTransition.setToX(0);

        // 组合动画
        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition, slideInTransition);
        sequentialTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
