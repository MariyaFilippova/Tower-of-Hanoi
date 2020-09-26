import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.Optional;


public class TowerHanoiApp extends Application {
    public static int NUM_CIRCLES = 3;
    private Optional<Circle> selectedCircle = Optional.empty();
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tower of Hanoi Application");
        TextField textField = new TextField();
        Button btn = new Button("Click");
        btn.setOnAction(event -> createContent(Integer.parseInt(textField.getText())));
        FlowPane root = new FlowPane(Orientation.VERTICAL, 200, 10, textField, btn);
        this.primaryStage = primaryStage;
        root.setPrefSize(1200, 300);
        Scene scene = new Scene(root, 250, 200);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    void setNumCircles(int i) {
        NUM_CIRCLES = i;
    }

    public class Tower extends StackPane {
        int numberOfCircles = 0;

        Tower(int x, int y, int numberOfCircles) {
            setTranslateX(x);
            setTranslateY(y);
            this.numberOfCircles = numberOfCircles;
            setPrefSize(400, 400);
            Rectangle bg = new Rectangle(25, 25);
            bg.setOnMouseClicked(e -> {
                if (selectedCircle.isPresent()) {
                    addCircle(selectedCircle.get());
                    selectedCircle = Optional.empty();
                }
                else {
                    selectedCircle = Optional.ofNullable(getTop());
                }
            });
            selectedCircle = Optional.empty();
            getChildren().add(bg);

        }

        private Circle getTop() {
            return getChildren().stream()
                    .filter(c -> c instanceof Circle)
                    .map(c -> (Circle) c)
                    .min(Comparator.comparingDouble(Circle::getRadius)).orElse(null);
        }

        private void addCircle(Circle circle) {
            this.numberOfCircles++;
            System.out.println(this.numberOfCircles);
            Circle top = getChildren().stream()
                    .filter(c -> c instanceof Circle)
                    .map(c -> (Circle) c)
                    .min(Comparator.comparingDouble(Circle::getRadius)).orElse(null);
            if (top == null) {
                getChildren().add(circle);
            } else {
                if (circle.getRadius() < top.getRadius()) {
                    getChildren().add(circle);
                }
            }
        }
    }


    private void createContent(int n) {
        setNumCircles(n);
        Pane root = new Pane();
        root.setPrefSize(1100, 400);
        for (int i = 0; i < 3; i++) {
            int numberOfCircles = i == 0 ? NUM_CIRCLES : 0;
            Tower tower = new Tower(i * 300, 0, numberOfCircles);
            if (i == 0) {
                for (int j = NUM_CIRCLES; j > 0; j--) {
                    Circle circle = new Circle(30 + 10 * j, null);
                    circle.setStroke(Color.BLACK);
                    tower.addCircle(circle);
                }
            }
            root.getChildren().add(tower);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}