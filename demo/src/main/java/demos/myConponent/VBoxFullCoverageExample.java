package demos.myConponent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;

public class VBoxFullCoverageExample extends Application {

    private VBox vbox;
    private StackPane root;
    private SplitPane splitPane;
    private boolean isMaximized = false;
    private double initialY;
    private double initialX;
    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();
        splitPane = new SplitPane();

        // 左侧面板
        Rectangle leftRect = new Rectangle(200, 400, Color.LIGHTBLUE);

        // 右侧VBox
        vbox = new VBox(10);
        vbox.setPrefSize(200, 400);
        vbox.setStyle("-fx-background-color: lightgreen;");
        vbox.setAlignment(Pos.TOP_LEFT);
        splitPane.getItems().addAll(leftRect, vbox);

        // 添加一个标签
        Label label = new Label("Click me to maximize!");

        // 添加一个TextArea
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(10);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        vbox.getChildren().addAll(label, textArea);

        // 设置点击事件
        vbox.setOnMouseClicked(event -> toggleMaximize());

        root.getChildren().add(splitPane);



        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("VBox Full Coverage Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        splitPane.setDividerPositions(0.5);
    }

    private void toggleMaximize() {
        if (!isMaximized) {
            // 保存VBox在SplitPane中的初始位置
             initialX = vbox.localToScene(vbox.getBoundsInLocal()).getMinX();
             initialY = vbox.localToScene(vbox.getBoundsInLocal()).getMinY();

            // 从SplitPane中移除VBox并添加到root
            Node removed = splitPane.getItems().remove(1);
            root.getChildren().add(removed);

            // 设置VBox的初始位置
            vbox.setTranslateX(initialX);
            vbox.setTranslateY(initialY);

            // 创建移动动画
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), vbox);
            translateTransition.setToX(0);
            translateTransition.setToY(0);

            // 创建缩放动画
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), vbox);
            scaleTransition.setToX(root.getWidth() / vbox.getWidth());
            scaleTransition.setToY(root.getHeight() / vbox.getHeight());

            // 并行执行动画
            //ParallelTransition parallelTransition = new ParallelTransition(translateTransition, scaleTransition);
            ParallelTransition parallelTransition = new ParallelTransition(translateTransition);
            parallelTransition.play();

            isMaximized = true;
        } else {

            // 创建还原动画
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), vbox);
            System.out.println(splitPane.localToScene(splitPane.getBoundsInLocal()).getMaxX() - vbox.getWidth());
            System.out.println("splitPaneMaxX "+initialX);
            translateTransition.setToX(initialX);
            translateTransition.setToY(0);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), vbox);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);

            //ParallelTransition parallelTransition = new ParallelTransition(translateT ransition, scaleTransition);
            ParallelTransition parallelTransition = new ParallelTransition(translateTransition);
            parallelTransition.setOnFinished(event -> {
                // 动画完成后，将VBox放回SplitPane
                root.getChildren().remove(vbox);
                splitPane.getItems().add(vbox);

                // 确保 VBox 可见
                vbox.setTranslateX(0);  // 复位 X 坐标
                vbox.setTranslateY(0);  // 复位 Y 坐标
                // 触发布局重新计算
                splitPane.requestLayout();
            });
            parallelTransition.play();

            isMaximized = false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
