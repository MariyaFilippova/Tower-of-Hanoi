import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.Optional;


public class TowerHanoiApp extends Application {
    public static final int NUM_CIRCLES = 3;
    private Optional<Circle> selectedCircle = Optional.empty();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tower of Hanoi Application");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public class Tower extends StackPane {
        Tower(int x, int y) {
            setTranslateX(x);
            setTranslateY(y);
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
                    .filter(c -> c instanceof  Circle)
                    .map(c -> (Circle) c)
                    .min(Comparator.comparingDouble(Circle::getRadius)).orElse(null);
        }

        private void addCircle(Circle circle) {
            Circle top = getChildren().stream()
                    .filter(c -> c instanceof  Circle)
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


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1200, 300);
        for (int i = 0; i <3; i++) {
            Tower tower = new Tower(i*300, 0);
            if (i == 0) {
                for (int j = NUM_CIRCLES; j > 0; j--) {
                    Circle circle = new Circle(30 + 20 * j, null);

                    circle.setStroke(Color.BLACK);
                    tower.addCircle(circle);
                }
            }
            root.getChildren().add(tower);
        }
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}