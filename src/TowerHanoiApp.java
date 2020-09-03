import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class TowerHanoiApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public static class Tower extends StackPane {
        Tower(int x, int y) {
            setTranslateX(x);
            setTranslateY(y);
            setPrefSize(300, 300);
            Rectangle bg = new Rectangle(25, 25);

            getChildren().add(bg);

        }
    }


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 300);
        for (int i = 0; i <3; i++) {
            root.getChildren().addAll(new Tower(i*300, 0));
        }
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}